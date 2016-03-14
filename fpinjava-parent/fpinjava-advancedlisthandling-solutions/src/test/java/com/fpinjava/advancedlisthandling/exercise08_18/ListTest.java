package com.fpinjava.advancedlisthandling.exercise08_18;

import com.fpinjava.common.Result;
import com.fpinjava.common.Tuple;
import org.junit.Test;

import static org.junit.Assert.*;


public class ListTest {

  @Test
  public void testUnfold() throws Exception {
    List<Integer> list = List.unfold_(0, i -> i < 10 ? Result.success(new Tuple<>(i, i + 1)) : Result.empty());
    assertEquals("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, NIL]", list.toString());
  }

  @Test
  public void testUnfoldEmpty() throws Exception {
    List<Integer> list = List.unfold_(0, i -> i < 0 ? Result.success(new Tuple<>(i, i + 1)) : Result.empty());
    assertEquals("[NIL]", list.toString());
  }

  @Test
  public void testUnfoldStackSafe() throws Exception {
    List<Integer> list = List.unfold(0, i -> i < 10 ? Result.success(new Tuple<>(i, i + 1)) : Result.empty());
    assertEquals("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, NIL]", list.toString());
  }

  @Test
  public void testUnfoldEmptyStackSafe() throws Exception {
    List<Integer> list = List.unfold(0, i -> i < 0 ? Result.success(new Tuple<>(i, i + 1)) : Result.empty());
    assertEquals("[NIL]", list.toString());
  }

  @Test
  public void testUnfoldBigStackSafe() throws Exception {
    List<Integer> list = List.unfold(0, i -> i < 10000 ? Result.success(new Tuple<>(i, i + 1)) : Result.empty());
    assertEquals(10000, list.length());
  }
}
