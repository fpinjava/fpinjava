package com.fpinjava.introduction.listing01_03;

import com.fpinjava.common.Tuple;

public class Cafe {
  
  public static Tuple<Coffee, Charge> buyCoffee(CreditCard cc) {
    return new Tuple<>(new Coffee(), new Charge(cc, Coffee.price));
  }
}
