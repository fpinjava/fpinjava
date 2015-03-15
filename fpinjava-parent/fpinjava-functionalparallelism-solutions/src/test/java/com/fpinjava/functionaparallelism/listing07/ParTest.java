package com.fpinjava.functionaparallelism.listing07;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.fpinjava.common.List;

public class ParTest {

  @Test
  public void test1() {
    ExecutorService es = Executors.newFixedThreadPool(4);
    Actor<String> echoer = Actor.apply(es, msg -> printMessage(msg), e -> {throw new IllegalStateException(e);});
    echoer.tell("Hello, world!");
    echoer.tell("Good bye, world!");
  }
  
  private void printMessage(String msg) {
    System.out.println("From " + Thread.currentThread().getName() + ": Got message: " + msg);
  }

  @Test
  public void test2() {
    ExecutorService es = Executors.newFixedThreadPool(2);
    Par<List<Integer>> p = Par.parMap(List.range(1, 10), x -> x * x);
    System.out.println(Par.run(es, p));
  }

}
