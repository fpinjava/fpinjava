package com.fpinjava.introduction.listing01_03;

import static com.fpinjava.introduction.listing01_03.Cafe.buyCoffee;
import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.Tuple;
import com.fpinjava.introduction.listing01_03.Charge;
import com.fpinjava.introduction.listing01_03.Coffee;
import com.fpinjava.introduction.listing01_03.CreditCard;

public class CafeTest {

  @Test
  public void testBuyCoffee() {
    CreditCard cc = new CreditCard();
    Tuple<Coffee, Charge> result = buyCoffee(cc);
    assertEquals(cc, result._2.cc);
    assertEquals(Coffee.price, result._2.amount);
  }

}
