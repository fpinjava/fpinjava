package com.fpinjava.introduction.listing01_04;

import static org.junit.Assert.*;
import static com.fpinjava.introduction.listing01_04.Cafe.*;

import org.junit.Test;

import com.fpinjava.common.List;
import com.fpinjava.common.Tuple;
import com.fpinjava.introduction.listing01_04.Charge;
import com.fpinjava.introduction.listing01_04.Coffee;
import com.fpinjava.introduction.listing01_04.CreditCard;

public class CafeTest {

  @Test
  public void testBuyCoffee() {
    CreditCard cc = new CreditCard();
    Tuple<Coffee, Charge> result = buyCoffee(cc);
    assertEquals(cc, result._2.cc);
    assertEquals(Coffee.price, result._2.amount);
  }

  @Test
  public void testBuyCoffees() {
    int numberOfCoffees = 5;
    CreditCard cc = new CreditCard();
    Tuple<List<Coffee>, Charge> result = buyCoffees(cc, numberOfCoffees);
    assertEquals(cc, result._2.cc);
    assertEquals(Coffee.price * numberOfCoffees, result._2.amount);
    assertEquals(numberOfCoffees, result._1.length());
  }

}
