package com.fpinjava.recursion.exercise04_04;

import java.util.List;

import com.fpinjava.recursion.listing04_03.TailCall;
import static com.fpinjava.recursion.listing04_03.TailCall.*;
import static com.fpinjava.common.CollectionUtilities.*;

public class Range {
  
  public static List<Integer> range(Integer start, Integer end) {
    return range_(list(), start, end).eval();
  }

  private static TailCall<List<Integer>> range_(List<Integer> acc, Integer start, Integer end) {
    return end <= start
        ? ret(acc)
        : sus(() -> range_(append(acc, start), start + 1, end));
  }
}
