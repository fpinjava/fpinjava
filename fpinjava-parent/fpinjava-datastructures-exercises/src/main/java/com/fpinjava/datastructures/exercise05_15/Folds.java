package com.fpinjava.datastructures.exercise05_15;

import com.fpinjava.common.Function;
import com.fpinjava.datastructures.exercise05_12.List;

public class Folds {
  
  public static <A, B> B foldRightViaFoldLeft(List<A> list, B identity, Function<A, Function<B, B>> f) {
    throw new IllegalStateException("To be implemented");
  }

  public static <A, B> B foldLeftViaFoldRight(List<A> list, B identity, Function<B, Function<A, B>> f) {
    throw new IllegalStateException("To be implemented");
  }
}
