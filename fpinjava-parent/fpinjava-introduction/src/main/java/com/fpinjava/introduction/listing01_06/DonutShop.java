package com.fpinjava.introduction.listing01_06;

import static com.fpinjava.common.List.fill;
import com.fpinjava.common.List;
import com.fpinjava.common.Tuple;

public class DonutShop {

  public static Tuple<Donut, Payment> buyDonut(final CreditCard cc) {
    return new Tuple<>(new Donut(), new Payment(cc, Donut.price));
  }

  public static Tuple<List<Donut>, Payment> buyDonuts(final int n,
                                                      final CreditCard creditCard) {
    return new Tuple<>(fill(n, Donut::new),
                               new Payment(creditCard, Donut.price * n));
  }
}
