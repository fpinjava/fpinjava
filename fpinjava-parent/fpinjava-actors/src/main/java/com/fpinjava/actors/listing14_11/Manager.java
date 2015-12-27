package com.fpinjava.actors.listing14_11;


import com.fpinjava.common.Effect;
import com.fpinjava.common.Executable;
import com.fpinjava.common.Function;
import com.fpinjava.common.Heap;
import com.fpinjava.common.List;
import com.fpinjava.common.Result;
import com.fpinjava.common.Tuple;

public class Manager extends AbstractActor<Tuple<Integer, Integer>> {

  private final Actor<Result<List<Integer>>> client;
  private final int workers;
  private final List<Tuple<Integer, Integer>> initial;
  private final List<Tuple<Integer, Integer>> workList;
  private final Heap<Tuple<Integer, Integer>> resultHeap;
  private final Function<Manager, Function<Behavior, Effect<Tuple<Integer, Integer>>>> managerFunction;

  public Manager(String id, List<Integer> list, Actor<Result<List<Integer>>> client, int workers) {
    super(id, Type.SERIAL);
    this.client = client;
    this.workers = workers;
    Tuple<List<Tuple<Integer, Integer>>, List<Tuple<Integer, Integer>>> splitLists = list.zipWithPosition().splitAt(this.workers);
    this.initial = splitLists._1;
    this.workList = splitLists._2;
    this.resultHeap = Heap.empty((t1, t2) -> t1._2.compareTo(t2._2));

    managerFunction = manager -> behavior -> i -> {
      Heap<Tuple<Integer, Integer>> result = behavior.resultHeap.insert(i);
      if (result.length() == list.length()) {
        this.client.tell(Result.success(result.toList().map(x -> x._1).reverse()));
      } else {
        manager.getContext()
            .become(new Behavior(behavior.workList
                .tailOption()
                .getOrElse(List.list()), result));
      }
    };
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

  private void tellClientEmptyResult(String string) {
    client.tell(Result.failure(string + " caused by empty input list."));
  }

  @Override
  public void onReceive(Tuple<Integer, Integer> message,
                        Result<Actor<Tuple<Integer, Integer>>> sender) {
    getContext().become(new Behavior(workList, resultHeap));
  }

  class Behavior implements MessageProcessor<Tuple<Integer, Integer>> {

    private final List<Tuple<Integer, Integer>> workList;
    private final Heap<Tuple<Integer, Integer>> resultHeap;

    private Behavior(List<Tuple<Integer, Integer>> workList, Heap<Tuple<Integer, Integer>> resultHeap) {
      this.workList = workList;
      this.resultHeap = resultHeap;
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
