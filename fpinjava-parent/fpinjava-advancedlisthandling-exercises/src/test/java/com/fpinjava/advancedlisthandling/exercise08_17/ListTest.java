package com.fpinjava.advancedlisthandling.exercise08_17;

import com.fpinjava.common.Map;
import org.junit.Test;

import static org.junit.Assert.*;


public class ListTest {

  private List<Payment> list = List.list(
      new Payment("Mickey", 160),
      new Payment("Minnie", 180),
      new Payment("Mickey", 112),
      new Payment("Donald", 84),
      new Payment("Mickey", 27),
      new Payment("Minnie", 421),
      new Payment("Mickey", 38),
      new Payment("Donald", 91),
      new Payment("Mickey", 24)
  );
  @Test
  public void testGroupBy_() throws Exception {
    Map<String, List<Payment>> map = list.groupByImperative(x -> x.name);
    assertEquals(Integer.valueOf(361), map.get("Mickey").map(list -> list.foldLeft(0, a -> b -> a + b.amount)).getOrElse(0));
    assertEquals(Integer.valueOf(601), map.get("Minnie").map(list -> list.foldLeft(0, a -> b -> a + b.amount)).getOrElse(0));
    assertEquals(Integer.valueOf(175), map.get("Donald").map(list -> list.foldLeft(0, a -> b -> a + b.amount)).getOrElse(0));
    assertEquals(Integer.valueOf(0), map.get("Goofy").map(list -> list.foldLeft(0, a -> b -> a + b.amount)).getOrElse(0));
  }

  @Test
  public void testGroupBy() throws Exception {
    Map<String, List<Payment>> map = list.groupBy(x -> x.name);
    assertEquals(Integer.valueOf(361), map.get("Mickey").map(list -> list.foldLeft(0, a -> b -> a + b.amount)).getOrElse(0));
    assertEquals(Integer.valueOf(601), map.get("Minnie").map(list -> list.foldLeft(0, a -> b -> a + b.amount)).getOrElse(0));
    assertEquals(Integer.valueOf(175), map.get("Donald").map(list -> list.foldLeft(0, a -> b -> a + b.amount)).getOrElse(0));
    assertEquals(Integer.valueOf(0), map.get("Goofy").map(list -> list.foldLeft(0, a -> b -> a + b.amount)).getOrElse(0));
  }

  public class Payment {
    public final String name;
    public final int amount;

    public Payment(String name, int amount) {
      this.name = name;
      this.amount = amount;
    }
  }
}
