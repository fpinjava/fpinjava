package com.fpinjava.introduction.listing01_05;

public class PureFunction {

  public static void main(String[] args) {
    
    String x = "Functional World";
    
    String r1 = sayHello(x);
    System.out.println(r1);
    
    String r2 = sayHello(x);
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