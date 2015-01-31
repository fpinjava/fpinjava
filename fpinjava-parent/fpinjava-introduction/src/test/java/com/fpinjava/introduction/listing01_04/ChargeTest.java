package com.fpinjava.introduction.listing01_04;

import static org.junit.Assert.*;
import static com.fpinjava.common.List.*;
import static com.fpinjava.introduction.listing01_04.Charge.*;

import org.junit.Test;

import com.fpinjava.common.Map;
import com.fpinjava.introduction.listing01_04.Charge;
import com.fpinjava.introduction.listing01_04.CreditCard;

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

  @Test
  public void testCoalesce() {
    CreditCard cc1 = new CreditCard();
    Charge charge11 = new Charge(cc1, 2);
    Charge charge12 = new Charge(cc1, 3);
    Charge charge13 = new Charge(cc1, 5);
    CreditCard cc2 = new CreditCard();
    Charge charge21 = new Charge(cc2, 4);
    Charge charge22 = new Charge(cc2, 6);
    Charge charge23 = new Charge(cc2, 2);
    CreditCard cc3 = new CreditCard();
    Charge charge31 = new Charge(cc3, 4);
    Charge charge32 = new Charge(cc3, 5);
    Map<CreditCard, Charge> charges = coalesce(list(charge11, charge12, charge13, charge21, charge22, charge23, charge31, charge32)).foldLeft(Map.empty(), x -> y -> x.put(y.cc, y));
    Charge result1 = charges.get(cc1).getOrThrow();
    Charge result2 = charges.get(cc2).getOrThrow();
    Charge result3 = charges.get(cc3).getOrThrow();
    assertEquals(10, result1.amount);
    assertEquals(cc1, result1.cc);
    assertEquals(12, result2.amount);
    assertEquals(cc2, result2.cc);
    assertEquals(9, result3.amount);
    assertEquals(cc3, result3.cc);
  }

}
