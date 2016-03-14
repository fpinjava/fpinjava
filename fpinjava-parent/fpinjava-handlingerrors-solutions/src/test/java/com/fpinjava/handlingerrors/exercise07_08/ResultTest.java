package com.fpinjava.handlingerrors.exercise07_08;

import static org.junit.Assert.*;

import org.junit.Test;

public class ResultTest {

  @Test
  public void testOfTValue() {
    assertEquals("Success(4)", Result.of(4).toString());
  }

  @Test
  public void testOfTNull() {
    assertEquals("Empty()", Result.of((Integer) null).toString());
  }

  @Test
  public void testOfTStringValue() {
    assertEquals("Success(4)", Result.of(4, "no value").toString());
  }

  @Test
  public void testOfTStringNull() {
    assertEquals("Failure(no value)", Result.of((Integer) null, "no value").toString());
  }

  @Test
  public void testOfFunctionOfTBooleanTValueTrue() {
    assertEquals("Success(4)", Result.of((Integer x) -> x % 2 == 0, 4).toString());
  }

  @Test
  public void testOfFunctionOfTBooleanTValueFalse() {
    assertEquals("Empty()", Result.of((Integer x) -> x % 2 == 0, 5).toString());
  }

  @Test
  public void testOfFunctionOfTBooleanTException() {
    assertEquals("Failure(Exception while evaluating predicate: 4)", Result.of((Integer x) -> {throw new RuntimeException("exception");}, 4).toString());
  }

  @Test
  public void testOfFunctionOfTBooleanTStringValueTrue() {
    assertEquals("Success(4)", Result.of((Integer x) -> x % 2 == 0, 4, "odd").toString());
  }

  @Test
  public void testOfFunctionOfTBooleanTStringValueFalse() {
    assertEquals("Failure(odd)", Result.of((Integer x) -> x % 2 == 0, 5, "odd").toString());
  }

  @Test
  public void testOfFunctionOfTBooleanTStringException() {
    assertEquals("Failure(Exception while evaluating predicate: odd)", Result.of((Integer x) -> {throw new RuntimeException("exception");}, 4, "odd").toString());
  }

}
