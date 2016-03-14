package com.fpinjava.makingjavafunctional.exercise03_04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class CollectionUtilities {

  public static <T> List<T > list() {
    return Collections.emptyList();
  }

  public static <T> List<T > list(T t) {
    return Collections.singletonList(t);
  }

  public static <T> List<T > list(List<T> ts) {
    return Collections.unmodifiableList(new ArrayList<>(ts));
  }

  @SafeVarargs
  public static <T> List<T > list(T... t) {
    return Collections.unmodifiableList(Arrays.asList(Arrays.copyOf(t, t.length)));
  }

  public static <T> T head(List<T> list) {
    if (list.size() == 0) {
      throw new IllegalStateException("head of empty list");
    }
    return list.get(0);
  }

  private static <T> List<T > copy(List<T> ts) {
    return new ArrayList<>(ts);
  }

  public static <T> List<T> tail(List<T> list) {
    if (list.size() == 0) {
      throw new IllegalStateException("tail of empty list");
    }
    List<T> workList = copy(list);
    workList.remove(0);
    return Collections.unmodifiableList(workList);
  }
}
