package com.fpinjava.actors.listing14_12;


public interface ActorContext<T> {

  void become(MessageProcessor<T> behavior);

  MessageProcessor<T> getBehavior();
}
