package com.fpinjava.introduction.listing01_02;

public class Cafe {
  
	public static Coffee buyCoffee(CreditCard cc, Payments p) {
		Coffee cup = new Coffee();
    p.charge(cc, Coffee.price);
		return cup;
	}
}
