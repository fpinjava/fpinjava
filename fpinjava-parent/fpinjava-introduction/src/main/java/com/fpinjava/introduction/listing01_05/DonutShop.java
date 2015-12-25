package com.fpinjava.introduction.listing01_05;

import static com.fpinjava.common.List.fill;
import com.fpinjava.common.List;
import com.fpinjava.common.Tuple;

public class DonutShop {

  public static Tuple<Donut, Payment> buyDonut(final CreditCard creditCard) {
    return new Tuple<>(new Donut(), new Payment(creditCard, Donut.price));
  }

  public static Tuple<List<Donut>, Payment> buyDonuts(final int quantity,
                                               final CreditCard creditCard) {
    return new Tuple<>(fill(quantity, Donut::new),
                               new Payment(creditCard, Donut.price * quantity));
  }
}
