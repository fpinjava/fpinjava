package com.fpinjava.makingjavafunctional.exercise03_13;

import static org.junit.Assert.*;

import org.junit.Test;

public class CollectionUtilitiesTest {

  @Test
  public void testRange() {
    assertEquals("[]", CollectionUtilities.range(0, 0).toString());
    assertEquals("[0]", CollectionUtilities.range(0, 1).toString());
    assertEquals("[0, 1, 2, 3, 4]", CollectionUtilities.range(0, 5).toString());
    assertEquals("[]", CollectionUtilities.range(5, 1).toString());
  }

}
