package com.fpinjava.lists.exercise05_22;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.lists.exercise05_21.List;

public class FilterTest {

  @Test
  public void testFilterViaFlatMap() {
    assertEquals("[2, 4, 6, NIL]", Filter.filterViaFlatMap(List.list(1, 2, 3, 4, 5, 6), x -> x % 2 == 0).toString());
  }

}
