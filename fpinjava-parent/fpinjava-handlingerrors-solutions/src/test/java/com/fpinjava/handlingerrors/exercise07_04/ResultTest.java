package com.fpinjava.handlingerrors.exercise07_04;

import static org.junit.Assert.*;

import org.junit.Test;

public class ResultTest {

  @Test
  public void testGetOrElseSuccess() {
    Result<Integer> result = Result.success(2);
    assertEquals(Integer.valueOf(2), result.getOrElse(ResultTest::getDefault));
  }

  @Test(expected = IllegalStateException.class)
  public void testGetOrElseFailure() {
    Result<Integer> result = Result.failure("error");
    result.getOrElse(ResultTest::getDefault);
  }

  @Test
  public void testOrElseSuccess() {
    Result<Integer> result = Result.success(2);
    assertEquals("Success(4)", result.map(x -> x * 2).orElse(() -> {throw new RuntimeException();}).toString());
  }

  @Test(expected=RuntimeException.class)
  public void testOrElseFailure() {
    Result<Integer> result = Result.failure("error");
    result.map(x -> x * 2).orElse(() -> {throw new RuntimeException();});
  }

  public static int getDefault() {
    throw new IllegalStateException();
  }

  @Test
  public void testMapSuccess() {
    Result<Integer> result = Result.success(2);
    assertEquals("Success(4)", result.map(x -> x * 2).toString());
  }

  @Test
  public void testMapFailure() {
    Result<Integer> result = Result.failure("error");
    assertEquals("Failure(error)", result.map(x -> x * 2).toString());
  }

  @Test
  public void testFlatMapSuccess() {
    Result<Integer> result = Result.success(2);
    assertEquals("Success(4)", result.flatMap(x -> Result.success(x * 2)).toString());
  }

  @Test
  public void testFlatMapFailure() {
    Result<Integer> result = Result.failure("error");
    assertEquals("Failure(error)", result.flatMap(x -> Result.success(x * 2)).toString());
  }

}
