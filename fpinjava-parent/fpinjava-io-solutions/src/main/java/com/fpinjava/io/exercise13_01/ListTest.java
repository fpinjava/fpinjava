package com.fpinjava.io.exercise13_01;


public class ListTest {

  public static void main(String... args) throws Exception {
    List<Integer> list = List.range(0, 10000);
    list.forEach(System.out::println);
  }
}
