package com.fpinjava.makingjavafunctional.exercise03_11;

import java.util.ArrayList;
import java.util.List;

import static com.fpinjava.makingjavafunctional.exercise03_10.CollectionUtilities.*;

public class Range {

  public static List<Integer> range(int start, int end) {
    List<Integer> result = new ArrayList<>();
    int temp = start;
    while (temp < end) {
      result = append(result, temp);
      temp = temp + 1;
    }
    return result;
  }
}
