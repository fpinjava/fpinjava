package com.fpinjava.makingjavafunctional.exercise03_08;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fpinjava.common.Function;

public class CollectionUtilities {

  public static <T> List<T> list() {
    return Arrays.asList();
  }

  public static <T> List<T> list(T t) {
    return Arrays.asList(t);
  }

  public static <T> List<T> list(List<T> ts) {
    return new ArrayList<>(ts);
  }

  @SafeVarargs
  public static <T> List<T> list(T... t) {
    return Arrays.asList(t);
  }

  public static <T> T head(List<T> list) {
    if (list.size() == 0) {
      throw new IllegalStateException("head of empty list");
    }
    return list.get(0);
  }

  private static <T> List<T> copy(List<T> ts) {
    return new ArrayList<>(ts);
  }

  public static <T> List<T> tail(List<T> list) {
    if (list.size() == 0) {
      throw new IllegalStateException("tail of empty list");
    }
    List<T> workList = copy(list);
    workList.remove(0);
    return workList;
  }

  public static <T, U> U foldLeft(List<T> ts,
                                  U identity,
                                  Function<U, Function<T, U>> f) {
    U result = identity;
    for (T t : ts) {
      result = f.apply(result).apply(t);
    }
    return result;
  }

  public static <T, U> U foldRight(List<T> ts, U identity,
                                   Function<T, Function<U, U>> f) {
    throw new RuntimeException("To be implemented");
  }
}
