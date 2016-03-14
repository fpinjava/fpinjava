package com.fpinjava.handlingerrors.exercise07_03;

import static org.junit.Assert.*;

import org.junit.Test;

public class EitherTest {

  @Test
  public void testGetOrElseRight() {
    Either<String, Integer> either = Either.right(2);
    assertEquals(Integer.valueOf(2), either.getOrElse(EitherTest::getDefault));
  }

  @Test(expected = IllegalStateException.class)
  public void testGetOrElseLeft() {
    Either<String, Integer> either = Either.left("error");
    assertEquals(Integer.valueOf(0), either.getOrElse(EitherTest::getDefault));
  }

  @Test
  public void testOrElseRight() {
    Either<String, Integer> either = Either.right(2);
    assertEquals("Right(4)", either.map(x -> x * 2).orElse(() -> {throw new RuntimeException();}).toString());
  }

  @Test(expected=RuntimeException.class)
  public void testOrElseLeft() {
    Either<String, Integer> either = Either.left("error");
    either.map(x -> x * 2).orElse(() -> {throw new RuntimeException();});
  }

  public static int getDefault() {
    throw new IllegalStateException();
  }

}
