package com.fpinjava.introduction.listing01_06;

public class ImpureFunction1 {

  public static void main(String[] args) {

    StringBuilder x = new StringBuilder("Hello, ");

    StringBuilder y = x.append("Functional World!");

    String r1 = y.toString();
    System.out.println(r1);

    String r2 = y.toString();
    System.out.println(r2);
  }
}