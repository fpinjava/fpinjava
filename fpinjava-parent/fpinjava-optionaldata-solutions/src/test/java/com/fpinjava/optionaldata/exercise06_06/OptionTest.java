package com.fpinjava.optionaldata.exercise06_06;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OptionTest {

  @Test
  public void testFilterTrue() {
    Option<Integer> option = Option.some(2);
    assertEquals("Some(2)", option.filter(x -> x % 2 == 0).toString());
  }

  @Test
  public void testFilterFalse() {
    Option<Integer> option = Option.some(3);
    assertEquals("None", option.filter(x -> x % 2 == 0).toString());
  }

  @Test
  public void testFilterNone() {
    Option<Integer> option = Option.none();
    assertEquals("None", option.filter(x -> x % 2 == 0).toString());
  }

}
