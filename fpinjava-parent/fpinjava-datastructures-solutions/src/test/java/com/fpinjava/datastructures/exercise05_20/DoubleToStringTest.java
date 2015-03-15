package com.fpinjava.datastructures.exercise05_20;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.datastructures.exercise05_18.List;

public class DoubleToStringTest {

  @Test
  public void testDoubleToString() {
    assertEquals("[0.0, 1.0, 2.0, 3.0, NIL]", DoubleToString.doubleToString(List.list(1.0, 2.0, 3.0)).cons("0.0").toString());
  }

}
