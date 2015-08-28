package com.fpinjava.laziness.listing09_01;


public class BooleanMethods {


    public static void main(String[] args) {
      System.out.println(or(true, true));
      System.out.println(or(true, false));
      System.out.println(or(false, true));
      System.out.println(or(false, false));

      System.out.println(and(true, true));
      System.out.println(and(true, false));
      System.out.println(and(false, true));
      System.out.println(and(false, false));

    }

  public static boolean or(boolean a, boolean b) {
    return a ? true : b ? true : false;
  }

  public static boolean and(boolean a, boolean b) {
    return a ? b ? true : false : false;
  }
}
