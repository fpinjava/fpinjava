package com.fpinjava.laziness.listing07_02;

import com.fpinjava.common.Supplier;

public class TestLaziness {

  public static void main(String[] args) {
    int a = 24;
    int b = 22;
    String message = if2(a < b, () -> getLower(a, b), () -> getHigher(a, b));
    System.out.println(message);
  }
  
  private static String getLower(int a, int b) {
    System.out.println("in getLower");
    return a + " < " + b;
  }
  
  private static String getHigher(int a, int b) {
    System.out.println("in getHigher");
    return a + " >= " + b;
  }
  
  static <A> A if2(boolean condition, Supplier<A> onTrue, Supplier<A> onFalse) {
      return condition 
          ? onTrue.get()
          : onFalse.get();
  }
}
