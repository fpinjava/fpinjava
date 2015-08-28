package com.fpinjava.advancedlisthandling.exercise08_13;

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
}
