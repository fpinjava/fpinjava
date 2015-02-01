package com.fpinjava.recursion.listing04_03;

import static org.junit.Assert.*;

import org.junit.Test;

public class TailCallTest {

  @Test
  public void test() {
    assertEquals(Integer.valueOf(100000003), Add.add(3, 100000000).eval());
  }

}
