package com.fpinjava.functionalparallelism.listing01;

import com.fpinjava.common.List;
import com.fpinjava.common.Tuple;

public class Listing01 {

  public static Integer sum(List<Integer> ints) {  //#A
    if (ints.length() <= 1) {
      return ints.headOption().getOrElse(0); //#B
    } else {
      final Tuple<List<Integer>, List<Integer>> tuple = ints.splitAt(ints.length() / 2); //#C
      return sum(tuple._1) + sum(tuple._2);  // #D
    }
  }
}
