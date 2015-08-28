package com.fpinjava.advancedlisthandling.exercise08_05;

import static org.junit.Assert.*;

import com.fpinjava.common.Function;
import com.fpinjava.common.Result;
import org.junit.Test;

public class ListTest {

  @Test
  public void testFlattenResult() {
    List<Integer> list = List.list(1, 2, 3, 4, 5, 0, 6, 7, 8, 9);
    List<Double> list2 = List.flattenResult(list.map(inverse));
    assertEquals("[1.0, 0.5, 0.3333333333333333, 0.25, 0.2, 0.16666666666666666, 0.14285714285714285, 0.125, 0.1111111111111111, NIL]", list2.toString());
  }

  private Function<Integer, Result<Double>> inverse = x -> x == 0 ? Result.failure("divide by 0") : Result.success((double) 1 / x);
}
