package com.fpinjava.actors.listing14_05;


import com.fpinjava.common.Result;

public interface MessageProcessor<T> {

  void process(T t, Result<Actor<T>> sender);
}
