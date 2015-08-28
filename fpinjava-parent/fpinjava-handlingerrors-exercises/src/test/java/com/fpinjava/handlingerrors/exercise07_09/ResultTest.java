package com.fpinjava.handlingerrors.exercise07_09;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Non functional method tested with a non functional test!
 */
public class ResultTest {

  public static class TestResult {
    int value;
  }
  Result<Integer> empty = Result.empty();
  Result<Integer> failure = Result.failure("failure message");
  Result<Integer> success = Result.success(4);

  @Test
  public void testForEachEmpty() {
    TestResult tr = new TestResult();
    empty.forEach(x -> tr.value = x);
    assertEquals(0, tr.value);
  }

  @Test
  public void testForEachFailure() {
    TestResult tr = new TestResult();
    failure.forEach(x -> tr.value = x);
    assertEquals(0, tr.value);
  }

  @Test
  public void testForEachSuccess() {
    TestResult tr = new TestResult();
    success.forEach(x -> tr.value = x);
    assertEquals(4, tr.value);
  }

}
