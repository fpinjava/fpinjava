package com.fpinjava.introduction.listing01_02;

public class Payments {

	private int amount;

  public void charge(CreditCard cc, int price) {
		this.amount += price;
	}

  public int getAmount() {
    return amount;
  }
}
