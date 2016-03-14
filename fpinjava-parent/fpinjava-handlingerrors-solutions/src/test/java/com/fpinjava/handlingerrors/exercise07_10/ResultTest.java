package com.fpinjava.handlingerrors.exercise07_10;

import static org.junit.Assert.*;

import org.junit.Test;

public class ResultTest {

  public static class TestResult {
    int value;
  }
  Result<Integer> empty = Result.empty();
  Result<Integer> failure = Result.failure("failure message");
  Result<Integer> success = Result.success(4);

  @Test
  public void testForEachOrThrowEmpty() {
    TestResult tr = new TestResult();
    empty.forEachOrThrow(x -> tr.value = x);
    assertEquals(0, tr.value);
  }

  @Test(expected = IllegalStateException.class)
  public void testForEachOrThrowFailure() {
    TestResult tr = new TestResult();
    failure.forEachOrThrow(x -> tr.value = x);
  }

  @Test
  public void testForEachOrThrowSuccess() {
    TestResult tr = new TestResult();
    success.forEachOrThrow(x -> tr.value = x);
    assertEquals(4, tr.value);
  }

}
