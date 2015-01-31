package com.fpinjava.common;

import static org.junit.Assert.*;

import org.junit.Test;

public class ListTest {

  @Test
  public void testHead() {
  }

  @Test
  public void testTail() {
  }

  @Test
  public void testIsEmpty() {
  }

  @Test
  public void testSetHeadA() {
  }

  @Test
  public void testDrop() {
  }

  @Test
  public void testDropWhile() {
  }

  @Test
  public void testAppend() {
  }

  @Test
  public void testInit() {
  }

  @Test
  public void testReverse() {
  }

  @Test
  public void testFoldRightBFunctionOfAFunctionOfBB() {
  }

  @Test
  public void testLength() {
  }

  @Test
  public void testFoldLeft() {
  }

  @Test
  public void testReduce() {
  }

  @Test
  public void testMap() {
  }

  @Test
  public void testFilter() {
  }

  @Test
  public void testFlatMap() {
  }

  @Test
  public void testConsA() {
  }

  @Test
  public void testList() {
  }

  @Test
  public void testListAArray() {
  }

  @Test
  public void testList__() {
  }

  @Test
  public void testList_() {
  }

  @Test
  public void testCopy_() {
  }

  @Test
  public void testCopy() {
  }

  @Test
  public void testSum() {
  }

  @Test
  public void testSumD() {
  }

  @Test
  public void testProduct() {
  }

  @Test
  public void testTailListOfA() {
  }

  @Test
  public void testSetHead_() {
  }

  @Test
  public void testSetHeadListOfAA() {
  }

  @Test
  public void testFoldRightListOfABFunctionOfAFunctionOfBB() {
  }

  @Test
  public void testSumViaFoldRight() {
  }

  @Test
  public void testProductViaFoldRight() {
  }

  @Test
  public void testSumViaFoldLeft() {
  }

  @Test
  public void testProductViaFoldLeft() {
  }

  @Test
  public void testLengthVaFoldLeft() {
  }

  @Test
  public void testReverseViaFoldLeft() {
  }

  @Test
  public void testReverseViaFoldRight() {
  }

  @Test
  public void testFoldRightViaFoldLeft() {
  }

  @Test
  public void testFoldLeftViaFoldRight() {
  }

  @Test
  public void testFoldRightViaFoldLeft2() {
  }

  @Test
  public void testFoldLeftViaFoldRight2() {
  }

  @Test
  public void testAppendViaFoldRight() {
  }

  @Test
  public void testConcat() {
  }

  @Test
  public void testAdd1() {
  }

  @Test
  public void testDoubleToString() {
  }

  @Test
  public void testFilterViaFlatMap() {
  }

  @Test
  public void testAddPairwise() {
  }

  @Test
  public void testAddPairwise_() {
  }

  @Test
  public void testZipWith() {
  }

  @Test
  public void testZipWith_() {
  }

  @Test
  public void testHasSubsequence() {
  }

  @Test
  public void testHasSubsequence_() {
  }

  @Test
  public void testStartsWith() {
  }

  @Test
  public void testStartsWith_() {
  }

  @Test
  public void testRange() {
  }

  @Test
  public void testRange_() {
  }

  @Test
  public void testFill() {
  }

  @Test
  public void testUnzipFunctionOfATupleOfA1A2() {
  }

  @Test
  public void testUnzipListOfTupleOfTU() {
  }

  @Test
  public void testGroupByListOfTFunction1OfTU() {
    List<Integer> list0 = List.list();
    List<Integer> list = List.list(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
    Function<Integer, Integer> f = u -> u - (u / 3) * 3;
    assertEquals(Map.empty().size(), List.groupBy(list0, f).size());
    Map<Integer, List<Integer>> map = List.groupBy(list, f);
    assertEquals("[3, 6, 9, 12, 15, 18, NIL]", map.get(0).getOrThrow().reverse().toString());
    assertEquals("[1, 4, 7, 10, 13, 16, 19, NIL]", map.get(1).getOrThrow().reverse().toString());
    assertEquals("[2, 5, 8, 11, 14, 17, 20, NIL]", map.get(2).getOrThrow().reverse().toString());
    assertEquals(List.NIL, map.get(3).getOrElse(List.<Integer> list()));
    assertEquals(3, map.size());
  } 

  @Test
  public void testGroupByFunction1OfTU() {
    List<Integer> list0 = List.list();
    List<Integer> list = List.list(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
    Function<Integer, Integer> f = u -> u - (u / 3) * 3;
    assertEquals(Map.empty().size(), list0.groupBy(f).size());
    Map<Integer, List<Integer>> map = list.groupBy(f);
    assertEquals("[3, 6, 9, 12, 15, 18, NIL]", map.get(0).getOrThrow().reverse().toString());
    assertEquals("[1, 4, 7, 10, 13, 16, 19, NIL]", map.get(1).getOrThrow().reverse().toString());
    assertEquals("[2, 5, 8, 11, 14, 17, 20, NIL]", map.get(2).getOrThrow().reverse().toString());
    assertEquals(List.NIL, map.get(3).getOrElse(List.<Integer> list()));
    assertEquals(3, map.size());
  }

  @Test
  public void testFromCollection() {
  }

  @Test
  public void testConsTListOfT() {
  }

  @Test
  public void testReverseListOfT() {
  }

}
