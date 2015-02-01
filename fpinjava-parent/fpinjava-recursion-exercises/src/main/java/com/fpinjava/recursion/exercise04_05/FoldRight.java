package com.fpinjava.recursion.exercise04_05;

import java.util.List;

import com.fpinjava.common.Function;
import com.fpinjava.recursion.listing04_03.TailCall;

import static com.fpinjava.recursion.listing04_03.TailCall.*;
import static com.fpinjava.common.CollectionUtilities.*;

public class FoldRight {

  public static <T, U> U foldRight(List<T> ts, U identity, Function<T, Function<U, U>> f) {
    throw new RuntimeException("To be implemented.");
  }
}
