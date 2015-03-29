package com.fpinjava.functionalparallelism.listing01;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.List;

public class Listing01Test {

  @Test
  public void testSum() {
    assertEquals(Integer.valueOf(0), Listing01.sum(List.list()));
  }

  @Test
  public void testSum1() {
    assertEquals(Integer.valueOf(5), Listing01.sum(List.list(5)));
  }

  @Test
  public void testSum2() {
    assertEquals(Integer.valueOf(13), Listing01.sum(List.list(5, 8)));
  }


  @Test
  public void testSum3() {
    assertEquals(Integer.valueOf(26), Listing01.sum(List.list(5, 8, 13)));
  }

  @Test
  public void testSum4() {
    assertEquals(Integer.valueOf(78), Listing01.sum(List.list(5, 8, 13, 26, 13, 8, 5)));
  }

  @Test
  public void testSum5() {
    assertEquals(Integer.valueOf(1_783_293_664), Listing01.sum(List.range(0, 1_000_000)));
  }

}
