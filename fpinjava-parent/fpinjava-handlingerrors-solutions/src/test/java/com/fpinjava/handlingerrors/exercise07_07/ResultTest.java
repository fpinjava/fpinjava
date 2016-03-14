package com.fpinjava.handlingerrors.exercise07_07;

import static org.junit.Assert.*;

import org.junit.Test;

public class ResultTest {

  static class TestException extends RuntimeException {
    public TestException() {
    }

    public TestException(String message) {
      super(message);
    }

    public TestException(String message, Throwable cause) {
      super(message, cause);
    }

    public TestException(Throwable cause) {
      super(cause);
    }
  }

  Result<Integer> empty = Result.empty();
  Result<Integer> failure = Result.failure("failure message");
  Result<Integer> success = Result.success(4);
  TestException testException = new TestException("test exception message");

  @Test
  public void testMapFailureStringEmpty() {
    assertEquals("Empty()", empty.mapFailure("no data").toString());
  }

  @Test
  public void testMapFailureStringFailure() {
    assertEquals("Failure(no data)", failure.mapFailure("no data").toString());
  }

  @Test
  public void testMapFailureStringSuccess() {
    assertEquals("Success(4)", success.mapFailure("no data").toString());
  }

  @Test
  public void testMapFailureStringExceptionEmpty() {
    assertEquals("Empty()", empty.mapFailure("no data", testException).toString());
  }

  @Test
  public void testMapFailureStringExceptionFailure() {
    assertEquals("Failure(no data)", failure.mapFailure("no data", testException).toString());
  }

  @Test
  public void testMapFailureStringExceptionSuccess() {
    assertEquals("Success(4)", success.mapFailure("no data", testException).toString());
  }

  @Test
  public void testMapFailureExceptionEmpty() {
    assertEquals("Empty()", empty.mapFailure(testException).toString());
  }

  @Test
  public void testMapFailureExceptionFailure() {
    assertEquals("Failure(test exception message)", failure.mapFailure(testException).toString());
  }

  @Test
  public void testMapFailureExceptionFailureThrow() {
    try {
      failure.mapFailure(testException).failIfEmpty("");
    } catch (IllegalStateException e) {
      assertEquals("test exception message", e.getMessage());
    }
  }

  @Test
  public void testMapFailureExceptionSuccess() {
    assertEquals("Success(4)", success.mapFailure(testException).toString());
  }

  @Test
  public void testFailIfEmptyEmpty() {
    assertEquals("Failure(no data)", empty.failIfEmpty("no data").toString());
  }

  @Test
  public void testFailIfEmptyFailure() {
    assertEquals("Failure(failure message)", failure.failIfEmpty("no data").toString());
  }

  @Test
  public void testFailIfEmptySuccess() {
    assertEquals("Success(4)", success.failIfEmpty("no data").toString());
  }
}
