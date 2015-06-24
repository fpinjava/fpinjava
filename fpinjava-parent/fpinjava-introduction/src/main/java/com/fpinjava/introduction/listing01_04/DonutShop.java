package com.fpinjava.introduction.listing01_04;

import com.fpinjava.common.Tuple;

public class DonutShop {

  public static Tuple<Donut, Payment> buyDonut(final CreditCard cc) {
    return new Tuple<>(new Donut(), new Payment(cc, Donut.price));
  }
}