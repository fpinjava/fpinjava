package com.fpinjava.recursion.exercise04_03;

import java.util.List;

import com.fpinjava.common.Function;
import com.fpinjava.recursion.listing04_03.TailCall;

import static com.fpinjava.recursion.listing04_03.TailCall.*;
import static com.fpinjava.common.CollectionUtilities.*;


public class FoldLeft {

  public static <T, U> U foldLeft(List<T> ts, U identity, Function<U, Function<T, U>> f) {
    throw new RuntimeException("To be implemented.");
  }
}
