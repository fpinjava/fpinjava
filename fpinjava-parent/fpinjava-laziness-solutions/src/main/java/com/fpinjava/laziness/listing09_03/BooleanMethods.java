package com.fpinjava.laziness.listing09_03;


import com.fpinjava.common.Supplier;

public class BooleanMethods {

  public static void main(String[] args) {
    System.out.println(getFirst() || getSecond());
    System.out.println(or(() -> getFirst(), () -> getSecond()));
  }

  public static boolean getFirst() {
    return true;
  }

  public static boolean getSecond() {
    throw new IllegalStateException();
  }

  public static boolean or(Supplier<Boolean> a, Supplier<Boolean> b) {
    return a.get() ? true : b.get() ? true : false;
  }

  public static boolean and(Supplier<Boolean> a, Supplier<Boolean> b) {
    return a.get() ? b.get() ? true : false : false;
  }
}
