package com.fpinjava.makingjavafunctional.exercise03_09;

import static org.junit.Assert.*;
import static com.fpinjava.makingjavafunctional.exercise03_09.CollectionUtilities.*;

import org.junit.Test;

public class CollectionUtilitiesTest {

  @Test
  public void testPrepend() {
    assertEquals("[0, 1, 2, 3]", prepend("0", list("1", "2", "3")).toString());
    assertEquals("[0]", prepend("0", list()).toString());
  }

  @Test
  public void testReverse() {
    assertEquals("[]", reverse(list()).toString());
    assertEquals("[1]", reverse(list(1)).toString());
    assertEquals("[3, 2, 1]", reverse(list(1, 2, 3)).toString());
  }

}
