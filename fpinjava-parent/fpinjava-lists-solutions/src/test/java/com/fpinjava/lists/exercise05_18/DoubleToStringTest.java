package com.fpinjava.lists.exercise05_18;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.lists.exercise05_16.List;

public class DoubleToStringTest {

  @Test
  public void testDoubleToString() {
    assertEquals("[0.0, 1.0, 2.0, 3.0, NIL]", DoubleToString.doubleToString(List.list(1.0, 2.0, 3.0)).cons("0.0").toString());
  }

}
