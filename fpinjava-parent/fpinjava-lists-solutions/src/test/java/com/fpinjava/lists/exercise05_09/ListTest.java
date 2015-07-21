package com.fpinjava.lists.exercise05_09;

import static com.fpinjava.lists.exercise05_09.List.list;
import static org.junit.Assert.*;

import org.junit.Test;

public class ListTest {

  @Test
  public void testLength() {
    assertEquals(0, list().length());
    assertEquals(3, list(1, 2, 3).length());
  }

}
