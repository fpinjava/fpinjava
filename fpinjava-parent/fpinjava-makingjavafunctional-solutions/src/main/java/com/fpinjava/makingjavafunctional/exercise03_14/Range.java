package com.fpinjava.makingjavafunctional.exercise03_14;

import java.util.List;

import static com.fpinjava.makingjavafunctional.exercise03_12.CollectionUtilities.*;

public class Range {

  public static List<Integer> range(Integer start, Integer end) {
    return end <= start
        ? list()
        : prepend(start, range(start + 1, end));
  }
}
