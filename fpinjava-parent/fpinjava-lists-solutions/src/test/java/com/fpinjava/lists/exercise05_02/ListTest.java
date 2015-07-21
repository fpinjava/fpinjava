package com.fpinjava.lists.exercise05_02;

import org.junit.Test;

import static com.fpinjava.lists.exercise05_02.List.*;
import static org.junit.Assert.assertEquals;

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
