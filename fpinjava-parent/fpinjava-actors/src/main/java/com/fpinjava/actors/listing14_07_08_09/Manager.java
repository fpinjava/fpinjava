package com.fpinjava.actors.listing14_07_08_09;


import com.fpinjava.actors.listing14_05.AbstractActor;
import com.fpinjava.actors.listing14_05.Actor;
import com.fpinjava.actors.listing14_05.MessageProcessor;
import com.fpinjava.actors.listing14_06.Worker;
import com.fpinjava.common.*;

public class Manager extends AbstractActor<Integer> {

  private final Actor<Result<List<Integer>>> client;
  private final int workers;
  private final List<Tuple<Integer, Integer>> initial;
  private final List<Integer> workList;
  private final List<Integer> resultList;
  private final Function<Manager, Function<Behavior,
      Effect<Integer>>> managerFunction;

  public Manager(String id, List<Integer> list,
                 Actor<Result<List<Integer>>> client, int workers) {
    super(id, Type.SERIAL);
    this.client = client;
    this.workers = workers;
    Tuple<List<Integer>, List<Integer>> splitLists =
        list.splitAt(this.workers);
    this.initial = splitLists._1.zipWithPosition();
    this.workList = splitLists._2;
    this.resultList = List.list();

    managerFunction = manager -> behavior -> i -> {
      List<Integer> result = behavior.resultList.cons(i);
      if (result.length() == list.length()) {
        this.client.tell(Result.success(result.reverse()));
      } else {
        manager.getContext()
            .become(new Behavior(behavior.workList
                .tailOption()
                .getOrElse(List.list()), result));
      }
    };
  }

  public void start() {
    onReceive(0, self());
    initial.sequence(this::initWorker)
        .forEachOrFail(this::initWorkers)
        .forEach(this::tellClientEmptyResult);
  }

  private Result<Executable> initWorker(Tuple<Integer, Integer> t) {
    return Result.success(() ->
        new Worker("Worker " + t._2, Type.SERIAL).tell(t._1, self()));
  }

  private void initWorkers(List<Executable> lst) {
    lst.forEach(Executable::exec);
  }

  private void tellClientEmptyResult(String string) {
    client.tell(Result.failure(string + " caused by empty input list."));
  }

  @Override
  public void onReceive(Integer message, Result<Actor<Integer>> sender) {
    getContext().become(new Behavior(workList, resultList));
  }

  class Behavior implements MessageProcessor<Integer> {

    private final List<Integer> workList;
    private final List<Integer> resultList;

    private Behavior(List<Integer> workList, List<Integer> resultList) {
      this.workList = workList;
      this.resultList = resultList;
    }

    @Override
    public void process(Integer i, Result<Actor<Integer>> sender) {
      managerFunction.apply(Manager.this).apply(Behavior.this).apply(i);
      sender.forEach(a -> workList.headOption().forEachOrFail(x -> a.tell(x, self()))
                                               .forEach(x -> a.shutdown()));
    }
  }
}
