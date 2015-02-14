package com.fpinjava.datastructures.exercise05_25;

import static org.junit.Assert.*;

import org.junit.Test;

public class ListTest {

  @Test
  public void testAddPairwise() {
    assertEquals("[3, 5, 7, NIL]", List.addPairwise(List.list(1, 2, 3), List.list(2, 3, 4)).toString());
    assertEquals("[3, 5, 7, NIL]", List.addPairwise(List.list(1, 2, 3, 4), List.list(2, 3, 4)).toString());
    assertEquals("[3, 5, 7, NIL]", List.addPairwise(List.list(1, 2, 3), List.list(2, 3, 4, 5)).toString());
  }

}
