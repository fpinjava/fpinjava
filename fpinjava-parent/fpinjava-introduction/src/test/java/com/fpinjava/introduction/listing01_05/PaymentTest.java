package com.fpinjava.introduction.listing01_05;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.List;
import com.fpinjava.common.Tuple;

public class PaymentTest {

  @Test
  public void testBuyDonuts() {
    CreditCard creditCard = new CreditCard();
    Tuple<List<Donut>, Payment> purchase = DonutShop.buyDonuts(5, creditCard);
    assertEquals(Donut.price * 5, purchase._2.amount);
    assertEquals(creditCard, purchase._2.creditCard);
  }
}
