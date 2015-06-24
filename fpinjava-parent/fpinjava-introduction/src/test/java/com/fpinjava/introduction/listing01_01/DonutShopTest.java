package com.fpinjava.introduction.listing01_01;

import static org.junit.Assert.*;
import static com.fpinjava.introduction.listing01_01.DonutShop.buyDonut;

import org.junit.Test;

import com.fpinjava.introduction.listing01_01.Donut;
import com.fpinjava.introduction.listing01_01.CreditCard;

public class DonutShopTest {

  @Test
  public void testBuyCoffee() {
    CreditCard crediCard = new CreditCard();
    buyDonut(crediCard);
    buyDonut(crediCard);
    assertEquals(Donut.price * 2, crediCard.getTotal());
  }

}
