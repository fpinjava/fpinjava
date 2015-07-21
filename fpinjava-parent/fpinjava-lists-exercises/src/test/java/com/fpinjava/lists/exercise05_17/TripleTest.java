package com.fpinjava.lists.exercise05_17;

import com.fpinjava.lists.exercise05_16.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TripleTest {

  @Test
  public void testTriple() {
    assertEquals("[2, 3, 4, 5, NIL]", Triple.triple(List.list(1, 2, 3, 4)).toString());
  }

}
