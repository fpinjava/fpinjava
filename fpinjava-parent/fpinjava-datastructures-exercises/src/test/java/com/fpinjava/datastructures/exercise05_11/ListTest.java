package com.fpinjava.datastructures.exercise05_11;

import static com.fpinjava.datastructures.exercise05_11.List.list;
import static org.junit.Assert.*;

import org.junit.Test;

public class ListTest {

  @Test
  public void testLength() {
    assertEquals(0, list().length());
    assertEquals(3, list(1, 2, 3).length());
  }

}
