package com.fpinjava.datastructures.exercise05_20;

import com.fpinjava.datastructures.exercise05_18.List;

public class DoubleToString {
  
  public static List<String> doubleToString(List<Double> list) {
    return List.foldRight(list, List.<String>list(), h -> t -> t.cons(Double.toString(h)));
  }
}
