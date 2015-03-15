package com.fpinjava.datastructures.exercise05_19;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.datastructures.exercise05_18.List;

public class Add1Test {

  @Test
  public void testAdd1() {
    assertEquals("[2, 3, 4, 5, NIL]", Add1.add1(List.list(1, 2, 3, 4)).toString());
  }

}
