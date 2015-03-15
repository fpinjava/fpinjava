package com.fpinjava.recursion.exercise04_08;

import java.util.List;

import com.fpinjava.common.Function;
import static com.fpinjava.common.CollectionUtilities.*;

public class ComposeAll {

  static <T> Function<T, T> composeAllViaFoldLeft(List<Function<T, T>> list) {
    return x -> foldLeft(reverse(list), x, a -> b -> b.apply(a));
  }

  static <T> Function<T, T> composeAllViaFoldRight(List<Function<T, T>> list) {
    return x -> foldRight(list, x, a -> a::apply);
  }

  static <T> Function<T, T> andThenAllViaFoldLeft(List<Function<T, T>> list) {
    return x -> foldLeft(list, x, a -> b -> b.apply(a));
  }

  static <T> Function<T, T> andThenAllViaFoldRight(List<Function<T, T>> list) {
    return x -> foldRight(reverse(list), x, a -> a::apply);
  }
}
