package com.fpinjava.makingjavafunctional.exercise03_14;

import com.fpinjava.makingjavafunctional.exercise03_13.CollectionUtilities;

import java.util.List;

public class Range {

  public static List<Integer> range(Integer start, Integer end) {
    return end <= start
        ? CollectionUtilities.list()
        : CollectionUtilities.prepend(start, range(start + 1, end));
  }
}
