package com.fpinjava.advancedlisthandling.exercise08_22;

import org.junit.Test;

import static org.junit.Assert.*;


public class ListTest {

  @Test
  public void testDivideLength() throws Exception {
    int testLimit = 2_000_000;
    int numSubList = 5000;

    List<Integer> testList = List.range(0, testLimit);
    List<List<Integer>> result = testList.divide(numSubList);
    /*
     * The length of the result should be equals to the greatest power of 2 smaller than the asked number,
     * as long the requested number of sub list is smaller than the length of the original list. Otherwise,
     * The list should be left untouched.
     */
    assertEquals(Integer.highestOneBit(numSubList <= testLimit ? Math.max(1, numSubList) : 1), result.length());
  }

  @Test
  public void testDivideAverageLength() throws Exception {
    int testLimit = 2_000_000;
    int numSubList = 5000;

    List<Integer> testList = List.range(0, testLimit);
    List<List<Integer>> result = testList.divide(numSubList);
    double averageLength = result.foldLeft(0, x -> y -> x + y.length()) / (double) result.length();
    /*
     * For all sub lists, length should not differ from average by more than one
     */
    assertTrue(result.forAll(x -> (double) x.length() - averageLength < 1));
  }

  @Test
  public void testDivideSumOfLength() throws Exception {
    int testLimit = 2_000_000;
    int numSubList = 5000;

    List<Integer> testList = List.range(0, testLimit);
    List<List<Integer>> result = testList.divide(numSubList);
    /*
     * The sum of the lengths of all sub lists should be equal to the length of the original list
     */
    assertEquals(Integer.valueOf(testList.length()), result.foldLeft(0, x -> y -> x + y.length()));
  }

  @Test
  public void testDivideConcat() throws Exception {
    int testLimit = 2_000_000;
    int numSubList = 5000;

    List<Integer> testList = List.range(0, testLimit);
    List<List<Integer>> result = testList.divide(numSubList);
    /*
     * Concatenating all sub lists should result into the original list
     */
    assertEquals(testList, result.flatMap(x -> x));
  }
}
