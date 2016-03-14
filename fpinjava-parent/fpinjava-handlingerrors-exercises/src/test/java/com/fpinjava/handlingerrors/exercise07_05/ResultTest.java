package com.fpinjava.handlingerrors.exercise07_05;

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
  public void testFilterFunctionOfVBooleanEmpty() {
    assertEquals("Empty()", empty.filter(even).toString());
    assertEquals("Empty()", empty.filter(odd).toString());
  }

  @Test
  public void testFilterFunctionOfVBooleanStringEmpty() {
    assertEquals("Empty()", empty.filter(even, "Condition is not matched").toString());
    assertEquals("Empty()", empty.filter(odd, "Condition is not matched").toString());
  }

  @Test
  public void testFilterFunctionOfVBooleanFailure() {
    assertEquals("Failure(Failure message)", failure.filter(even).toString());
    assertEquals("Failure(Failure message)", failure.filter(odd).toString());
  }

  @Test
  public void testFilterFunctionOfVBooleanStringFailure() {
    assertEquals("Failure(Failure message)", failure.filter(even, "Condition is not matched").toString());
    assertEquals("Failure(Failure message)", failure.filter(odd, "Condition is not matched").toString());
  }

  @Test
  public void testFilterFunctionOfVBooleanSuccess() {
    assertEquals("Success(4)", success.filter(even).toString());
    assertEquals("Failure(Condition not matched)", success.filter(odd).toString());
  }

  @Test
  public void testFilterFunctionOfVBooleanStringSuccess() {
    assertEquals("Success(4)", success.filter(even, "The number is not even").toString());
    assertEquals("Failure(The number is not even)", success.filter(odd, "The number is not even").toString());
  }

}
