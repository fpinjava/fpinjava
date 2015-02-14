package com.fpinjava.datastructures.exercise05_24;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.datastructures.exercise05_23.List;

public class FilterTest {

  @Test
  public void testFilterViaFlatMap() {
    assertEquals("[2, 4, 6, NIL]", Filter.filterViaFlatMap(List.list(1, 2, 3, 4, 5, 6), x -> x % 2 == 0).toString());
  }

}
