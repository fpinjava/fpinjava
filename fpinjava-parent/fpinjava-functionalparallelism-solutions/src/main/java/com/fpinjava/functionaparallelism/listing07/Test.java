package com.fpinjava.functionaparallelism.listing07;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


public class Test {

  private static AtomicReference<Node<String>> tail = new AtomicReference<>(new Node<String>("start"));
  private static AtomicInteger suspended = new AtomicInteger(1);
  private static AtomicReference<Node<String>> head = new AtomicReference<>(tail.get());

  public static void tell(String a) {
    Node<String> n = new Node<String>(a);
    head.getAndSet(n).lazySet(n);
    //trySchedule();
  }

  @SuppressWarnings("serial")
  static class Node<A> extends AtomicReference<Node<A>> {

    private A a;

    public Node() {
      super();
    }

    public Node(A a) {
      super();
      this.a = a;
    }

    public A getA() {
      return a;
    }

//    public void setA(A a) {
//      this.a = a;
//    }

  }

  public static void main(String[] args) {
    System.out.println("head: " + head.get().getA());
    System.out.println("tail: " + tail.get().getA());
    tell("1");
    tell("2");
    tell("3");
    tell("4");
    System.out.println("head: " + head.get().getA());
    System.out.println("head: " + tail.get().get().getA());
    System.out.println("head: " + tail.get().get().get().getA());
    System.out.println("head: " + tail.get().get().get().get().getA());
  }
}
