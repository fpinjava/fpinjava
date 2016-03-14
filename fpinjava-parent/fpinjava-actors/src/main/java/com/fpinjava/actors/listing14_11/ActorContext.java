package com.fpinjava.actors.listing14_11;


public interface ActorContext<T> {

  void become(MessageProcessor<T> behavior);

  MessageProcessor<T> getBehavior();
}
