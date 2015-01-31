package com.fpinjava.makingjavafunctional.exercise03_04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CollectionUtilities {

  public static <T> List<T > list() {
    return Arrays.asList();
  }

  public static <T> List<T > list(T t) {
    return Arrays.asList(t);
  }
  public static <T> List<T > list(List<T> ts) {
    return new ArrayList<>(ts);
  }

  @SafeVarargs
  public static <T> List<T > list(T... t) {
    return Arrays.asList(t);
  }
  
  public static <T> T head(List<T> list) {
    throw new RuntimeException("To be implemented");
  }

  private static <T> List<T > copy(List<T> ts) {
    throw new RuntimeException("To be implemented");
  }

  public static <T> List<T> tail(List<T> list) {
    throw new RuntimeException("To be implemented");
  }
}
