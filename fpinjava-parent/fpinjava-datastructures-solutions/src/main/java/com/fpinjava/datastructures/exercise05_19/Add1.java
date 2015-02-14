package com.fpinjava.datastructures.exercise05_19;

import com.fpinjava.datastructures.exercise05_18.List;

public class Add1 {

  public static List<Integer> add1(List<Integer> list) {
    return List.foldRight(list, List.<Integer>list(), h -> t -> t.cons(h + 1));
  }
}
