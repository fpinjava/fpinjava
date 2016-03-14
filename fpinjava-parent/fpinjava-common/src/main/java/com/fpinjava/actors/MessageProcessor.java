package com.fpinjava.actors;


import com.fpinjava.common.Result;

public interface MessageProcessor<T> {

  void process(T t, Result<Actor<T>> sender);
}
