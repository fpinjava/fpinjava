package com.fpinjava.functionalparallelism.listing07;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HelloActor {

  public static void main(String... args) {
    ExecutorService es = Executors.newFixedThreadPool(1);
    Actor<String> echoer = Actor.apply(es, HelloActor::printMessage, e -> {throw new IllegalStateException(e);});
    echoer.tell("Hello, world!");
    es.shutdown();
  }

  private static void printMessage(String msg) {
    System.out.println("From " + Thread.currentThread().getName() + ": Got message: " + msg);
  }
}
