package com.fpinjava.actors.listing14_12;


import com.fpinjava.common.Effect;
import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Result;

public class Receiver extends AbstractActor<Integer> {

  private final Actor<List<Integer>> client;
  private final Function<Receiver, Function<Behavior, Effect<Integer>>> receiverFunction;

  public Receiver(String id, Type type, Actor<List<Integer>> client) {
    super(id, type);
    this.client = client;
    receiverFunction = receiver -> behavior -> i -> {
      if (i == -1) {
        this.client.tell(behavior.resultList.reverse());
        shutdown();
      } else {
        receiver.getContext()
            .become(new Behavior(behavior.resultList.cons(i)));
      }
    };
  }

  @Override
  public void onReceive(Integer i, Result<Actor<Integer>> sender) {
    getContext().become(new Behavior(List.list(i)));
  }

  class Behavior implements MessageProcessor<Integer> {

    private final List<Integer> resultList;

    private Behavior(List<Integer> resultList) {
      this.resultList = resultList;
    }

    @Override
    public void process(Integer i, Result<Actor<Integer>> sender) {
      receiverFunction.apply(Receiver.this).apply(Behavior.this).apply(i);
    }
  }
}
