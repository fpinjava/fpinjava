package com.fpinjava.datastructures.exercise05_15;

import com.fpinjava.common.Function;
import com.fpinjava.datastructures.exercise05_12.List;

public class Folds {
  
  public static <A, B> B foldRightViaFoldLeft(List<A> list, B identity, Function<A, Function<B, B>> f) {
    return list.reverse().foldLeft(identity, x -> y -> f.apply(y).apply(x));
  }

  public static <A, B> B foldLeftViaFoldRight(List<A> list, B identity, Function<B, Function<A, B>> f) {
    return List.foldRight(list.reverse(), identity, x -> y -> f.apply(y).apply(x));
  }
  
  public static <A, B> B foldRightViaFoldLeft2(List<A> list, B identity, Function<A, Function<B, B>> f) {
    return list.foldLeft(Function.<B>identity(), g -> a -> b -> g.apply(f.apply(a).apply(b))).apply(identity);
  }

  public static <A, B> B foldLeftViaFoldRight2(List<A> list, B identity, Function<B, Function<A, B>> f) {
    return List.foldRight(list, Function.<B>identity(), a -> g -> b -> g.apply(f.apply(b).apply(a))).apply(identity);
  }
}
