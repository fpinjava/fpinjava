package com.fpinjava.errorhandling.listing06_01;

import static org.junit.Assert.*;

import org.junit.Test;

public class FailingTest {

  @Test(expected=RuntimeException.class)
  public void testFailingFn() {
    Failing.failingFn(10);
  }

  @Test
  public void testFailingFn2() {
    assertEquals(Integer.valueOf(43), Failing.failingFn2(10));
  }

}
