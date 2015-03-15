package com.fpinjava.datastructures.exercise05_21;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.datastructures.exercise05_21.List;

public class ListTest {

  @Test
  public void testMap() {
    assertEquals("[2, 3, 4, 5, NIL]", List.list(1, 2, 3, 4).map(x -> x + 1).toString());
    assertEquals("[0.0, 1.0, 2.0, 3.0, NIL]", List.list(1.0, 2.0, 3.0).map(x -> x.toString()).cons("0.0").toString());
  }

}
