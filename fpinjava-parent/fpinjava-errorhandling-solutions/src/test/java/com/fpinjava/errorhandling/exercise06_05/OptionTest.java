package com.fpinjava.errorhandling.exercise06_05;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.List;

public class OptionTest {

  @Test
  public void testSequenceViaTraverse() {
    List<String> list = List.list("1", "2", "3", "4", "5");
    assertEquals("Some([1, 2, 3, 4, 5, NIL])", 
        Option.sequenceViaTraverse(list.map(i -> Insurance.validate(() -> Integer.valueOf(i)))).toString());
  }

  @Test
  public void testSequenceViaTraverseError() {
    List<String> list = List.list("1", "2", "3", "4,5", "5");
    assertEquals("None", 
        Option.sequenceViaTraverse(list.map(i -> Insurance.validate(() -> Integer.valueOf(i)))).toString());
  }

  @Test
  public void testSequenceViaTraverseViaFoldRight() {
    List<String> list = List.list("1", "2", "3", "4", "5");
    assertEquals("Some([1, 2, 3, 4, 5, NIL])", 
        Option.sequenceViaTraverseViaFoldRight(list.map(i -> Insurance.validate(() -> Integer.valueOf(i)))).toString());
  }

  @Test
  public void testSequenceViaTraverseViaFoldRightError() {
    List<String> list = List.list("1", "2", "3", "4,5", "5");
    assertEquals("None", 
        Option.sequenceViaTraverseViaFoldRight(list.map(i -> Insurance.validate(() -> Integer.valueOf(i)))).toString());
  }

}
