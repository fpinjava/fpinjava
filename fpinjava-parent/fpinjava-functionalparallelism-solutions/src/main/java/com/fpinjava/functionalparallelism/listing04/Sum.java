package com.fpinjava.functionalparallelism.listing04;

import com.fpinjava.common.List;
import com.fpinjava.common.Tuple;

public class Sum {

  public static Par<Integer> sum(List<Integer> ints) {
    if (ints.length() <= 1) {
      return Par.unit(ints.headOption().getOrElse(0));
    } else {
      final Tuple<List<Integer>, List<Integer>> tuple = 
                                            ints.splitAt(ints.length() / 2);
      return Par.map2(Par.fork(() -> sum(tuple._1)), Par.fork(() -> sum(tuple._2)), x -> y -> x + y);
    }
  }
}
