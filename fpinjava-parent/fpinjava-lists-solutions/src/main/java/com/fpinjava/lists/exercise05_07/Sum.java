package com.fpinjava.lists.exercise05_07;

import com.fpinjava.lists.exercise05_06.List;

public class Sum {

  public static Integer sum(List<Integer> ints) {
    return ints.isEmpty()
        ? 0
        : ints.head() + sum(ints.tail());
  }
}
