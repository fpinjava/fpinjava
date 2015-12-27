package com.fpinjava.actors.listing14_02;


import com.fpinjava.actors.listing14_01.Actor;
import com.fpinjava.common.Result;

public interface MessageProcessor<T> {

  void process(T t, Result<Actor<T>> sender);
}
