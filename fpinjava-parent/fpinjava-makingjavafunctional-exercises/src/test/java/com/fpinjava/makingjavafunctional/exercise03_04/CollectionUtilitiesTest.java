package com.fpinjava.makingjavafunctional.exercise03_04;

import static com.fpinjava.makingjavafunctional.exercise03_03.CollectionUtilities.list;
import static com.fpinjava.makingjavafunctional.exercise03_04.CollectionUtilities.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class CollectionUtilitiesTest {

  @Test(expected=IllegalStateException.class)
  public void testHeadEmpty() {
    List<String> list = list();
    head(list);
  }

  @Test
  public void testHead() {
    List<String> list = list("1");
    assertEquals("1", head(list));
    List<String> list2 = list("1", "2", "3", "4");
    assertEquals("1", head(list2));
  }

  @Test(expected=IllegalStateException.class)
  public void testTailEmpty() {
    List<String> list = list();
    tail(list);
  }

  @Test
  public void testTail() {
    List<String> list0 = list("1", "2", "3", "4");
    List<String> list = tail(list0);
    assertEquals(3, list.size());
    assertEquals("2", list.get(0));
    assertEquals("3", list.get(1));
    assertEquals("4", list.get(2));
  }
}
