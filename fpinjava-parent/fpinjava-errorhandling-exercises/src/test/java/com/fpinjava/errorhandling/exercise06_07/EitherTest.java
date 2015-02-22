package com.fpinjava.errorhandling.exercise06_07;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.List;

public class EitherTest {

  @Test
  public void testSequence() {
    List<String> list = List.list("1", "2", "3", "4", "5");
    assertEquals("Right([1, 2, 3, 4, 5, NIL])", 
        Either.sequence(list.map(i -> Insurance.validate(() -> Integer.valueOf(i)))).toString());
  }

  @Test
  public void testSequenceError() {
    List<String> list = List.list("1", "2", "3", "4,5", "5");
    assertEquals("Left(java.lang.NumberFormatException: For input string: \"4,5\")", 
        Either.sequence(list.map(i -> Insurance.validate(() -> Integer.valueOf(i)))).toString());
  }

  @Test
  public void testSequenceViaTraverseRecursive() {
    List<String> list = List.list("1", "2", "3", "4", "5");
    assertEquals("Right([1, 2, 3, 4, 5, NIL])", 
        Either.sequenceViaTraverseRecursive(list.map(i -> Insurance.validate(() -> Integer.valueOf(i)))).toString());
  }

  @Test
  public void testSequenceViaTraverseRecursiveError() {
    List<String> list = List.list("1", "2", "3", "4,5", "5");
    assertEquals("Left(java.lang.NumberFormatException: For input string: \"4,5\")", 
        Either.sequenceViaTraverseRecursive(list.map(i -> Insurance.validate(() -> Integer.valueOf(i)))).toString());
  }

}
