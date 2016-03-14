package com.fpinjava.advancedlisthandling.exercise08_14;

import org.junit.Test;

import static org.junit.Assert.*;


public class ListTest {

  @Test
  public void testSplitAt() throws Exception {
    List<Integer> list = List.list(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
    assertEquals("([1, 2, 3, 4, 5, NIL],[6, 7, 8, 9, 10, 11, 12, 13, 14, NIL])", list.splitAt(5).toString());
    assertEquals("([NIL],[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, NIL])", list.splitAt(0).toString());
    assertEquals("([NIL],[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, NIL])", list.splitAt(-2).toString());
    assertEquals("([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, NIL],[14, NIL])", list.splitAt(13).toString());
    assertEquals("([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, NIL],[NIL])", list.splitAt(14).toString());
    assertEquals("([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, NIL],[NIL])", list.splitAt(15).toString());
  }
}
