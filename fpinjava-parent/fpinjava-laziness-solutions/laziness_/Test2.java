package com.fpinjava.laziness;

import com.fpinjava.common.Option;
import com.fpinjava.laziness.listing07_03.Stream;

public class Test2 {

  public static void main(String[] args) {
    Stream<Integer> tl = Stream.empty();
    Stream<Integer> x = Stream.cons(() -> expensive(), tl);
    Option<Integer> h1 = x.headOption();
    Option<Integer> h2 = x.headOption();
    System.out.println(h1.get());
    System.out.println(h2.get());
  }

  private static Integer expensive() {
    System.out.println("x");
    return 1;
  }

  //      return tail().foldRightStackBased(() -> f.apply(head()).apply(z), f);

}
