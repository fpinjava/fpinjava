package com.fpinjava.introduction.listing01_04;

import com.fpinjava.common.List;

public class Charge {
  
  public final CreditCard cc;
  public final int amount;

  public Charge(CreditCard cc, int amount) {
    this.cc = cc;
    this.amount = amount;
  }
  
  public Charge combine(Charge other) {
    if (cc.equals(other.cc)) {
      return new Charge(cc, amount + other.amount); 
    } else {
      throw new RuntimeException("Can't combine charges to different cards");
    }
  }
  
  public static List<Charge> coalesce(List<Charge> charges) {
    return charges
        .groupBy(x -> x.cc)
        .values()
        .map(x -> x.reduce(c1 -> c2 -> c1.combine(c2)));
  }
}