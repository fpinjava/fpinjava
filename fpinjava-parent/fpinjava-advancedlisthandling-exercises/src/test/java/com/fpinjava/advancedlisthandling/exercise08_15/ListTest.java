package com.fpinjava.advancedlisthandling.exercise08_15;

import org.junit.Test;

import static org.junit.Assert.*;


public class ListTest {

  @Test
  public void testGetAt__() throws Exception {
    List<Integer> list = List.list(1, 3, 5, 7, 9, 11, 13);
    assertEquals("Failure(java.lang.IllegalStateException: Index out of bound)", list.getAt__(-2).toString());
    assertEquals("Success(1)", list.getAt__(0).toString());
    assertEquals("Success(3)", list.getAt__(1).toString());
    assertEquals("Success(5)", list.getAt__(2).toString());
    assertEquals("Success(7)", list.getAt__(3).toString());
    assertEquals("Success(9)", list.getAt__(4).toString());
    assertEquals("Success(11)", list.getAt__(5).toString());
    assertEquals("Success(13)", list.getAt__(6).toString());
    assertEquals("Failure(java.lang.IllegalStateException: Index out of bound)", list.getAt__(7).toString());
    assertEquals("Failure(java.lang.IllegalStateException: Index out of bound)", list.getAt__(9).toString());
  }

  @Test
  public void testSplitAt__() throws Exception {
    List<Integer> list = List.list(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
    assertEquals("([NIL],[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, NIL])", list.splitAt__(0).toString());
    assertEquals("([1, NIL],[2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, NIL])", list.splitAt__(1).toString());
    assertEquals("([1, 2, NIL],[3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, NIL])", list.splitAt__(2).toString());
    assertEquals("([1, 2, 3, NIL],[4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, NIL])", list.splitAt__(3).toString());
    assertEquals("([1, 2, 3, 4, NIL],[5, 6, 7, 8, 9, 10, 11, 12, 13, 14, NIL])", list.splitAt__(4).toString());
    assertEquals("([1, 2, 3, 4, 5, NIL],[6, 7, 8, 9, 10, 11, 12, 13, 14, NIL])", list.splitAt__(5).toString());
    assertEquals("([NIL],[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, NIL])", list.splitAt__(-2).toString());
    assertEquals("([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, NIL],[14, NIL])", list.splitAt__(13).toString());
    assertEquals("([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, NIL],[NIL])", list.splitAt__(14).toString());
    assertEquals("([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, NIL],[NIL])", list.splitAt__(15).toString());
  }
}
