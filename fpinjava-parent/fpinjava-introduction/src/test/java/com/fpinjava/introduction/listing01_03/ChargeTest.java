package com.fpinjava.introduction.listing01_03;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChargeTest {

  @Test
  public void testCharge() {
    CreditCard cc = new CreditCard();
    Charge charge = new Charge(cc, 2);
    assertEquals(cc, charge.cc);
    assertEquals(2, charge.amount);
  }

  @Test
  public void testCombine() {
    CreditCard cc = new CreditCard();
    Charge charge1 = new Charge(cc, 2);
    Charge charge2 = new Charge(cc, 3);
    Charge charge3 = charge1.combine(charge2);
    assertEquals(cc, charge3.cc);
    assertEquals(5, charge3.amount);
  }

}
