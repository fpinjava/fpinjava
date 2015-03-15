package com.fpinjava.introduction.listing01_06;

public class ImpureFunction {

  public static void main(String[] args) {

    StringBuilder x = new StringBuilder("Hello, ");

    StringBuilder y = x.append("Functional World!");

    String r1 = y.toString();
    System.out.println(r1);

    String r2 = y.toString();
    System.out.println(r2);
  }

  public static String reverse(String string) {
    StringBuilder builder = new StringBuilder();
    for(int i = string.length() - 1; i >= 0; i--) {
      builder.append(string.charAt(i));
    }
    return builder.toString();
  }

  public static String sayHello(String string) {
    return "Hello, " + string + "!";
  }
}