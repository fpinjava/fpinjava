package com.fpinjava.introduction.listing01_03;

import com.fpinjava.common.Tuple;

public class DonutShop {
  
  public static Tuple<Donut, Payment> buyDonut(CreditCard creditCard) {
    return new Tuple<>(new Donut(), new Payment(creditCard, Donut.price));
  }
}
