package com.fpinjava.datastructures.exercise05_23;

import static org.junit.Assert.*;

import org.junit.Test;

public class ListTest {

  @Test
  public void testFlatMap() {
    assertEquals("[1, 1, 2, 2, 3, 3, NIL]", List.list(1,2,3).flatMap(i -> List.list(i,i)).toString());
  }

}
