package com.fpinjava.makingjavafunctional.exercise03_03;

import static org.junit.Assert.*;
import static com.fpinjava.makingjavafunctional.exercise03_03.CollectionUtilities.list;

import java.util.List;

import org.junit.Test;

public class CollectionUtilitiesTest {

  @Test
  public void testList() {
    List<Integer> list = list();
    assertEquals(0, list.size());
  }

  @Test
  public void testListT() {
    String s = "s";
    List<String> list = list(s);
    assertEquals(1, list.size());
    assertEquals(s, list.get(0));
  }

  @Test
  public void testListListOfT() {
    List<String> list0 = list("1", "2", "3", "4");
    List<String> list = list(list0);    
    assertEquals(4, list.size());
    assertEquals("1", list.get(0));
    assertEquals("2", list.get(1));
    assertEquals("3", list.get(2));
    assertEquals("4", list.get(3));
  }

  @Test
  public void testListTArray() {
    List<String> list = list("1", "2", "3", "4");
    assertEquals(4, list.size());
    assertEquals("1", list.get(0));
    assertEquals("2", list.get(1));
    assertEquals("3", list.get(2));
    assertEquals("4", list.get(3));
  }
}
