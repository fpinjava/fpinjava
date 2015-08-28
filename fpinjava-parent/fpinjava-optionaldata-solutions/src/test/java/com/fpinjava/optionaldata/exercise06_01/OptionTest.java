package com.fpinjava.optionaldata.exercise06_01;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OptionTest {

  @Test
  public void testGetOrElse() {
    Option<Integer> option = Option.some(2);
    assertEquals(Integer.valueOf(2), option.getOrElse(0));
  }

  @Test
  public void testGetOrElseNone() {
    Option<Integer> option = Option.none();
    assertEquals(Integer.valueOf(0), option.getOrElse(0));
  }
}
