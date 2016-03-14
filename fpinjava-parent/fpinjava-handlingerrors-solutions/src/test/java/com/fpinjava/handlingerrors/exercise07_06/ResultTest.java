package com.fpinjava.handlingerrors.exercise07_06;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.Function;

public class ResultTest {

  Result<Integer> empty = Result.empty();
  Result<Integer> failure = Result.failure("Failure message");
  Result<Integer> success = Result.success(4);
  Function<Integer, Boolean> even = x -> x % 2 == 0;
  Function<Integer, Boolean> odd = x -> !even.apply(x);

  @Test
  public void testExistsFunctionOfVBooleanEmpty() {
    assertFalse(empty.exists(even));
    assertFalse(empty.exists(odd));
  }

  @Test
  public void testExistsFunctionOfVBooleanFailure() {
    assertFalse(failure.exists(even));
    assertFalse(failure.exists(odd));
  }

  @Test
  public void testExistsFunctionOfVBooleanSuccess() {
    assertTrue(success.exists(even));
    assertFalse(success.exists(odd));
  }

}
