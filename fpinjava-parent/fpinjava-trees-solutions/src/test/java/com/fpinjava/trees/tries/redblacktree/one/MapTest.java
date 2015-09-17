package com.fpinjava.trees.tries.redblacktree.one;

import org.junit.Test;

import static org.junit.Assert.*;


public class MapTest {

  @Test
  public void testApply() throws Exception {
    Map<Integer, String> map = Map.<Integer, String>empty().put(34, "thirty four").put(22, "twenty two").put(12, "twelve").put(45, "forty five").put(18, "eighteen");
    assertTrue(map.containsKey(34));
    assertFalse(map.containsKey(17));
    assertTrue(map.containsKey(22));
    assertFalse(map.containsKey(23));
    assertTrue(map.containsKey(12));
    assertFalse(map.containsKey(7));
    assertTrue(map.containsKey(45));
    assertFalse(map.containsKey(28));
    assertTrue(map.containsKey(18));
    assertFalse(map.containsKey(14));
    assertEquals("thirty four", map.get(34).getOrElse("Not in Map"));
    assertEquals("Not in Map", map.get(17).getOrElse("Not in Map"));
    assertEquals("twenty two", map.get(22).getOrElse("Not in Map"));
    assertEquals("Not in Map", map.get(23).getOrElse("Not in Map"));
    assertEquals("twelve", map.get(12).getOrElse("Not in Map"));
    assertEquals("Not in Map", map.get(7).getOrElse("Not in Map"));
    assertEquals("forty five", map.get(45).getOrElse("Not in Map"));
    assertEquals("Not in Map", map.get(28).getOrElse("Not in Map"));
    assertEquals("eighteen", map.get(18).getOrElse("Not in Map"));
    assertEquals("Not in Map", map.get(14).getOrElse("Not in Map"));
  }
}
