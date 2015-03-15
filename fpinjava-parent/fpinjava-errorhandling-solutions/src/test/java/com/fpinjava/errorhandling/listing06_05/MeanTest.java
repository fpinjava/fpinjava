package com.fpinjava.errorhandling.listing06_05;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.List;

public class MeanTest {

  @Test
  public void testMeanRight() {
    List<Integer> list = List.list(1, 2, 3, 4, 5);
    assertEquals("Right(3.0)", Mean.mean(list).toString());
  }

  @Test
  public void testMeanLeft() {
    List<Integer> list = List.list();
    assertEquals("Left(Mean of empty list!)", Mean.mean(list).toString());
  }

  @Test
  public void testSafeDivRight() {
    assertEquals("Right(3)", Mean.safeDiv(29, 9).toString());
  }

  @Test
  public void testSafeDivLeft() {
    assertEquals("Left(java.lang.ArithmeticException: / by zero)", Mean.safeDiv(29, 0).toString());
  }

  @Test
  public void testValidateRight() {
    assertEquals("Right(3.5)", Mean.validate(() -> Double.parseDouble("3.5")).toString());
  }

  @Test
  public void testValidateLeft() {
    assertEquals("Left(java.lang.NumberFormatException: For input string: \"3,5\")", 
        Mean.validate(() -> Double.parseDouble("3,5")).toString());
  }

}
