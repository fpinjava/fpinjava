package com.fpinjava.datastructures.exercise05_14;

import static com.fpinjava.datastructures.exercise05_12.List.*;
import static com.fpinjava.datastructures.exercise05_14.Reverse.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class ReverseTest {

  @Test
  public void testReverseViaFoldLeft() {
    assertEquals("[NIL]", reverseViaFoldLeft(list()).toString());
    assertEquals("[3, 2, 1, NIL]", reverseViaFoldLeft(list(1, 2, 3)).toString());
  }

}
