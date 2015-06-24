package com.fpinjava.introduction.listing01_06;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.List;
import com.fpinjava.common.Map;
import com.fpinjava.common.Tuple;

public class PaymentTest {

  @Test
  public void testGroupByCard() {
    CreditCard creditCard1 = new CreditCard();
    CreditCard creditCard2 = new CreditCard();
    Tuple<List<Donut>, Payment> purchase1 = DonutShop.buyDonuts(5, creditCard1);
    Tuple<List<Donut>, Payment> purchase2 = DonutShop.buyDonuts(3, creditCard2);
    Tuple<List<Donut>, Payment> purchase3 = DonutShop.buyDonuts(2, creditCard1);
    Tuple<List<Donut>, Payment> purchase4 = DonutShop.buyDonuts(1, creditCard1);
    Tuple<List<Donut>, Payment> purchase5 = DonutShop.buyDonuts(4, creditCard2);
    List<Payment> paymentList = Payment.groupByCard(List.list(purchase1._2, purchase2._2, purchase3._2, purchase4._2, purchase5._2));
    assertEquals(2, paymentList.length());
    Payment payment1 = paymentList.head();
    Payment payment2 = paymentList.tail().head();
    Map<CreditCard, Integer> payments = new Map<CreditCard, Integer>().put(payment1.creditCard, payment1.amount).put(payment2.creditCard, payment2.amount);
    assertEquals(16, payments.get(creditCard1).getOrThrow().intValue());
    assertEquals(14, payments.get(creditCard2).getOrThrow().intValue());
  }
}
