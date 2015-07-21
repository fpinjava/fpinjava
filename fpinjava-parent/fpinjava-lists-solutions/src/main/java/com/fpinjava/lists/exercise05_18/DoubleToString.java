package com.fpinjava.lists.exercise05_18;

import com.fpinjava.lists.exercise05_16.List;

public class DoubleToString {

  public static List<String> doubleToString(List<Double> list) {
    return List.foldRight(list, List.<String>list(), h -> t -> t.cons(Double.toString(h)));
  }
}
