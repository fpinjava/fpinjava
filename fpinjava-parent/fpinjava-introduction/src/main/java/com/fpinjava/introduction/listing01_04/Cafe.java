package com.fpinjava.introduction.listing01_04;

import static com.fpinjava.common.List.fill;
import com.fpinjava.common.List;
import com.fpinjava.common.Tuple;

public class Cafe {

  public static Tuple<Coffee, Charge> buyCoffee(final CreditCard cc) {
    return new Tuple<>(new Coffee(), new Charge(cc, Coffee.price));
  }

  public static Tuple<List<Coffee>, Charge> buyCoffees(final CreditCard cc,
                                                       final int n) {
    final Tuple<List<Coffee>, List<Charge>> purchases = 
                               fill(n, () -> buyCoffee(cc)).unzip(x -> x);
    return new Tuple<>(purchases._1, purchases._2.reduce(c1 -> c1::combine));
  }
}