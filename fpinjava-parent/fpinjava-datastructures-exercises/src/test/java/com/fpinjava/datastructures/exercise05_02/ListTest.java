package com.fpinjava.datastructures.exercise05_02;

import static org.junit.Assert.*;

import org.junit.Test;

import static com.fpinjava.datastructures.exercise05_02.List.*;

public class ListTest {

  @Test(expected=IllegalStateException.class)
  public void testSetHeadNil() {
    list().setHead("d");
  }

  @Test
  public void testSetHead() {
    assertEquals("d", list("a", "b", "c").setHead("d").head());
    assertEquals("b", list("a", "b", "c").setHead("d").tail().head());
  }

}
