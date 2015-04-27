package com.fpinjava.introduction.listing01_03;

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
}