package com.fpinjava.introduction.listing01_01;

public class Cafe {
  
  public static Coffee buyCoffee(CreditCard cc) {
    Coffee cup = new Coffee();
    cc.charge(Coffee.price);
    return cup;
  }
}
