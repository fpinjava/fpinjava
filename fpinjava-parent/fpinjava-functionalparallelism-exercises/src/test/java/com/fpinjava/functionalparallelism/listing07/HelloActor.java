package com.fpinjava.functionalparallelism.listing07;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HelloActor {

  public static void main(String... args) {
ExecutorService es = Executors.newFixedThreadPool(1);
Actor<String> echoer = Actor.apply(es, msg -> System.out.println("Got message: " + msg));
echoer.tell("Hello!");
echoer.tell("Goodbye!");
echoer.tell("You're just repeating everything I say, aren't you?");
es.shutdown();
  }
}
