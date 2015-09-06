package com.fpinjava.handlingerrors.exercise07_16;

import org.junit.Test;

import static org.junit.Assert.*;


public class ResultTest {

  @Test
  public void testGetOrElseViaFoldLeftSuccess() {
    Result<Integer> result = Result.success(1);
    assertEquals(Integer.valueOf(1), result.getOrElseViaFoldLeft(0));
  }

  @Test
  public void testGetOrElseViaFoldLeftEmpty() {
    Result<Integer> result = Result.empty();
    assertEquals(Integer.valueOf(0), result.getOrElseViaFoldLeft(0));
  }

  @Test
  public void testGetOrElseViaFoldRightSuccess() {
    Result<Integer> result = Result.success(1);
    assertEquals(Integer.valueOf(1), result.getOrElseViaFoldRight(0));
  }

  @Test
  public void testGetOrElseViaFoldRightEmpty() {
    Result<Integer> result = Result.empty();
    assertEquals(Integer.valueOf(0), result.getOrElseViaFoldRight(0));
  }
}
