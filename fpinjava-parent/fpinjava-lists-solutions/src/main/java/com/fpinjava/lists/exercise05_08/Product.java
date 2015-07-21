package com.fpinjava.lists.exercise05_08;

import com.fpinjava.lists.exercise05_06.List;

public class Product {

  public static Double product(List<Double> ints) {
    return ints.isEmpty()
        ? 1
        : ints.head() * product(ints.tail());
  }
}
