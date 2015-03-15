package com.fpinjava.errorhandling.listing06_01;

public class Failing {

  public static Integer failingFn(Integer i) {
    final int y = getInteger();
    try {
      final int x = 42 + 5;
      return x + y;
    } catch (RuntimeException e) {
      return 43;
    }
  }
  
  public static Integer failingFn2(Integer i) {
    try {
      final int x = 42 + 5;
      return x + getInteger();
    } catch (RuntimeException e) {
      return 43;
    }
  }

  public static Integer getInteger() {
    throw new RuntimeException("fail!");
  } 
}
