package com.fpinjava.actors.listing14_10;


public interface ActorContext<T> {

  void become(MessageProcessor<T> behavior);

  MessageProcessor<T> getBehavior();
}
