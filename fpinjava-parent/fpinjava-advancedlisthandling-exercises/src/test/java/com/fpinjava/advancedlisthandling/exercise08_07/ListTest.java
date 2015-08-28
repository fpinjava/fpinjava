package com.fpinjava.advancedlisthandling.exercise08_07;

import static org.junit.Assert.*;

import com.fpinjava.common.Function;
import com.fpinjava.common.Result;
import org.junit.Test;

public class ListTest {

  @Test
  public void testSequence1() {
    List<Integer> list = List.list(1, 2, 3, 4, 5, 6, 7, 8, 9);
    Result<List<Double>> list2 = List.sequence(list.map(inverse));
    assertEquals("Success([1.0, 0.5, 0.3333333333333333, 0.25, 0.2, 0.16666666666666666, 0.14285714285714285, 0.125, 0.1111111111111111, NIL])", list2.toString());
  }

  @Test
  public void testSequence2() {
    List<Integer> list = List.list(1, 2, 3, 4, 5, 0, 6, 7, 8, 9);
    Result<List<Double>> list2 = List.sequence(list.map(inverse));
    assertEquals("Failure(java.lang.IllegalStateException: divide by 0)", list2.toString());
  }

  private Function<Integer, Result<Double>> inverse = x -> x == 0 ? Result.failure("divide by 0") : Result.success((double) 1 / x);

}
