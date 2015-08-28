package com.fpinjava.optionaldata.exercise06_02;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OptionTest {

  @Test
  public void testGetOrElse() {
    Option<Integer> option = Option.some(2);
//    assertEquals(Integer.valueOf(2), option.getOrElse(OptionTest::getDefault));
  }

  @Test(expected = IllegalStateException.class)
  public void testGetOrElseNone() {
    Option<Integer> option = Option.none();
//    assertEquals(Integer.valueOf(0), option.getOrElse(OptionTest::getDefault));
  }

  public static int getDefault() {
    throw new IllegalStateException();
  }
}
