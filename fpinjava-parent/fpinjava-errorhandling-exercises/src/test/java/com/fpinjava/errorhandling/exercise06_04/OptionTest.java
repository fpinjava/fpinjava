package com.fpinjava.errorhandling.exercise06_04;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.List;

public class OptionTest {

  @Test
  public void testSequence() {
    List<String> list = List.list("1", "2", "3", "4", "5");
    assertEquals("Some([1, 2, 3, 4, 5, NIL])", Option.sequence(list.map(i -> Insurance.validate(() -> Integer.valueOf(i)))).toString());
  }

  @Test
  public void testSequenceError() {
    List<String> list = List.list("1", "2,5", "3", "4", "5");
    assertEquals("None", Option.sequence(list.map(i -> Insurance.validate(() -> Integer.valueOf(i)))).toString());
  }

  @Test
  public void testSequence_1() {
    List<String> list = List.list("1", "2", "3", "4", "5");
    assertEquals("Some([1, 2, 3, 4, 5, NIL])", Option.sequenceViaFoldRight(list.map(i -> Insurance.validate(() -> Integer.valueOf(i)))).toString());
  }

  @Test
  public void testSequence_1Error() {
    List<String> list = List.list("1", "2,5", "3", "4", "5");
    assertEquals("None", Option.sequenceViaFoldRight(list.map(i -> Insurance.validate(() -> Integer.valueOf(i)))).toString());
  }

}
