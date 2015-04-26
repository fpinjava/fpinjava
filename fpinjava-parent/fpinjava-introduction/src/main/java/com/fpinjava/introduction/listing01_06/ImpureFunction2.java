package com.fpinjava.introduction.listing01_06;

public class ImpureFunction2 {

  public static void main(String[] args) {

    StringBuilder x = new StringBuilder("Hello, ");

    String r1 = x.append("Functional World!").toString();
    System.out.println(r1);

    String r2 = x.append("Functional World!").toString();
    System.out.println(r2);
  }
}