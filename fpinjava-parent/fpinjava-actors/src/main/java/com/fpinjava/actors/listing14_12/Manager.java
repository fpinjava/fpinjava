package com.fpinjava.actors.listing14_12;


import com.fpinjava.common.Effect;
import com.fpinjava.common.Executable;
import com.fpinjava.common.Function;
import com.fpinjava.common.Heap;
import com.fpinjava.common.List;
import com.fpinjava.common.Result;
import com.fpinjava.common.Tuple;
import com.fpinjava.common.Tuple3;

public class Manager extends AbstractActor<Tuple<Integer, Integer>> {

  private final Actor<Integer> client;
  private final int workers;
  private final List<Tuple<Integer, Integer>> initial;
  private final List<Tuple<Integer, Integer>> workList;
  private final Heap<Tuple<Integer, Integer>> resultHeap;
  private final Function<Manager, Function<Behavior, Effect<Tuple<Integer, Integer>>>> managerFunction;
  private final int limit;

  public Manager(String id, List<Integer> list, Actor<Integer> client, int workers) {
    super(id, Type.SERIAL);
    this.client = client;
    this.workers = workers;
    this.limit = list.length() - 1;
    Tuple<List<Tuple<Integer, Integer>>, List<Tuple<Integer, Integer>>> splitLists = list.zipWithPosition().splitAt(this.workers);
    this.initial = splitLists._1;
    this.workList = splitLists._2;
    this.resultHeap = Heap.empty((t1, t2) -> t1._2.compareTo(t2._2));

    managerFunction = manager -> behavior -> t -> {
      Tuple3<Heap<Tuple<Integer, Integer>>, Integer, List<Integer>> result =
          streamResult(behavior.resultHeap.insert(t),
              behavior.expected, List.list());
      result._3.reverse().forEach(this.client::tell);
      if (result._2 > limit) {
        this.client.tell(-1);
      } else {
        manager.getContext()
            .become(new Behavior(behavior.workList.tailOption()
                .getOrElse(List.list()), result._1, result._2));
      }
    };
  }

  private Tuple3<Heap<Tuple<Integer, Integer>>, Integer, List<Integer>> streamResult(Heap<Tuple<Integer, Integer>> result, int expected, List<Integer> list) {
    Tuple3<Heap<Tuple<Integer, Integer>>, Integer, List<Integer>> tuple3 = new Tuple3<>(result, expected, list);
    Result<Tuple3<Heap<Tuple<Integer, Integer>>, Integer, List<Integer>>> temp = result.head().flatMap(head ->
        result.tail().map(tail -> head._2 == expected
            ? streamResult(tail, expected + 1, list.cons(head._1))
            : tuple3));
    return temp.getOrElse(tuple3);
  }

  public void start() {
    onReceive(new Tuple<>(0, 0), self());
    initial.sequence(this::initWorker)
        .forEachOrFail(this::initWorkers)
        .forEach(this::tellClientEmptyResult);
  }

  private Result<Executable> initWorker(Tuple<Integer, Integer> t) {
    return Result.success(() -> new Worker("Worker " + t._2,
        Type.SERIAL).tell(new Tuple<>(t._1, t._2), self()));
  }

  private void initWorkers(List<Executable> lst) {
    lst.forEach(Executable::exec);
  }

  private void tellClientEmptyResult(String ignore) {
    client.tell(-1);
  }

  @Override
  public void onReceive(Tuple<Integer, Integer> message,
                        Result<Actor<Tuple<Integer, Integer>>> sender) {
    getContext().become(new Behavior(workList, resultHeap, 0));
  }

  class Behavior implements MessageProcessor<Tuple<Integer, Integer>> {

    private final List<Tuple<Integer, Integer>> workList;
    private final Heap<Tuple<Integer, Integer>> resultHeap;
    private final int expected;

    private Behavior(List<Tuple<Integer, Integer>> workList,
                     Heap<Tuple<Integer, Integer>> resultHeap, int expected) {
      this.workList = workList;
      this.resultHeap = resultHeap;
      this.expected = expected;
    }

    @Override
    public void process(Tuple<Integer, Integer> i,
                        Result<Actor<Tuple<Integer, Integer>>> sender) {
      managerFunction.apply(Manager.this).apply(Behavior.this).apply(i);
      sender.forEach(a -> workList.headOption().forEachOrFail(x -> a.tell(x, self()))
                                               .forEach(x -> a.shutdown()));
    }
  }
}
