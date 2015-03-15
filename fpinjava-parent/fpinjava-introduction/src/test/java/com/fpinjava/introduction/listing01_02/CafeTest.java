package com.fpinjava.introduction.listing01_02;

import static org.junit.Assert.*;
import static com.fpinjava.introduction.listing01_02.Cafe.buyCoffee;

import org.junit.Test;

import com.fpinjava.introduction.listing01_02.Coffee;
import com.fpinjava.introduction.listing01_02.CreditCard;
import com.fpinjava.introduction.listing01_02.Payments;

public class CafeTest {

  @Test
  public void testBuyCoffee() {
    CreditCard cc = new CreditCard();
    Payments p = new Payments();
    buyCoffee(cc, p);
    buyCoffee(cc, p);
    assertEquals(Coffee.price * 2, p.getAmount());
  }

}
