package com.fpinjava.actors.listing14_02;


public interface ActorContext<T> {

  void become(MessageProcessor<T> behavior);

  MessageProcessor<T> getBehavior();
}
