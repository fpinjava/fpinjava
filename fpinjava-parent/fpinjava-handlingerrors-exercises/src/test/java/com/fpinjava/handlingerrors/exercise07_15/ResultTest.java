package com.fpinjava.handlingerrors.exercise07_15;

import org.junit.Test;

import static org.junit.Assert.*;


public class ResultTest {

  @Test
  public void testfoldLeftSuccess() {
    Result<Character> result = Result.success('a');
    assertEquals("_a", result.foldLeft("_", x -> y -> x + y));
  }

  @Test
  public void testfoldRightSuccess() {
    Result<Character> result = Result.success('a');
    assertEquals("a_", result.foldRight("_", x -> y -> x + y));
  }

  @Test
  public void testFoldLeftFailure() {
    Result<Character> result = Result.failure("error");
    assertEquals("_", result.foldLeft("_", x -> y -> x + y));
  }

  @Test
  public void testFoldRightFailure() {
    Result<Character> result = Result.failure("error");
    assertEquals("_", result.foldRight("_", x -> y -> x + y));
  }
}
