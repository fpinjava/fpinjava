package com.fpinjava.introduction.listing01_01;

public class DonutShop {
  
  public static Donut buyDonut(CreditCard creditCard) {
    Donut donut = new Donut();
    creditCard.charge(Donut.price);
    return donut;
  }
}
