package com.fpinjava.advancedlisthandling.exercise08_11;

import com.fpinjava.common.Tuple;
import org.junit.Test;

import static org.junit.Assert.*;


public class ListTest {

  @Test
  public void testUnzip() throws Exception {
    CreditCard cc1 = new CreditCard("Mickey");
    CreditCard cc2 = new CreditCard("Donald");
    Payment p1 = new Payment(cc1, 120);
    Payment p2 = new Payment(cc1, 75);
    Payment p3 = new Payment(cc2, 143);
    Payment p4 = new Payment(cc2, 62);
    Payment p5 = new Payment(cc1, 240);
    List<Payment> payments = List.list(p1, p2, p3, p4, p5);
    Tuple<List<CreditCard>, List<Integer>> result = payments.unzip(x -> new Tuple<>(x.creditCard, x.amount));
    List<CreditCard> creditCards = result._1;
    List<Integer> amounts = result._2;
    assertEquals("[Mickey, Mickey, Donald, Donald, Mickey, NIL]", creditCards.map(cc -> cc.owner).toString());
    assertEquals("[120, 75, 143, 62, 240, NIL]", amounts.toString());
  }

  public static class Payment {

    public final CreditCard creditCard;
    public final int amount;

    public Payment(CreditCard creditCard, int amount) {
      this.creditCard = creditCard;
      this.amount = amount;
    }
  }

  public static class CreditCard {
    public final String owner;

    public CreditCard(String owner) {
      this.owner = owner;
    }
  }
}
