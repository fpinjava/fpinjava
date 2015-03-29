package com.fpinjava.functionalparallelism.listing02;

import com.fpinjava.common.List;
import com.fpinjava.common.Tuple;

public class Listing02 {

  public static Integer sum(List<Integer> ints) { 
    if (ints.length() <= 1) {
      return ints.headOption().getOrElse(0);
    } else {
      final Tuple<List<Integer>, List<Integer>> tuple = ints.splitAt(ints.length() / 2);
      final Par<Integer> sumL = Par.unit(() -> sum(tuple._1)); //#A
      final Par<Integer> sumR = Par.unit(() -> sum(tuple._2)); //#B
      return Par.get(sumL) + Par.get(sumR);                    // #C
    }
  }
}
