package com.fpinjava.introduction.listing01_01;

import static org.junit.Assert.*;
import static com.fpinjava.introduction.listing01_01.Cafe.buyCoffee;

import org.junit.Test;

import com.fpinjava.introduction.listing01_01.Coffee;
import com.fpinjava.introduction.listing01_01.CreditCard;

public class CafeTest {

  @Test
  public void testBuyCoffee() {
    CreditCard cc = new CreditCard();
    buyCoffee(cc);
    buyCoffee(cc);
    assertEquals(Coffee.price * 2, cc.getTotal());
  }

}
