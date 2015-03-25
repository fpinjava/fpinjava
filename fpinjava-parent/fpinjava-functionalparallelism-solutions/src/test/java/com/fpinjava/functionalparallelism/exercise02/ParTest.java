package com.fpinjava.functionalparallelism.exercise02;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.fpinjava.common.List;
import com.fpinjava.common.Tuple;


public class ParTest {

  @Test
  public void testSum() {
    ExecutorService s = Executors.newCachedThreadPool();
    try {
      assertEquals(Integer.valueOf(0), Par.run(s, sum(List.list())).get());
      assertEquals(Integer.valueOf(1), Par.run(s, sum(List.list(1))).get());
      assertEquals(Integer.valueOf(3), Par.run(s, sum(List.list(1, 2))).get());
      assertEquals(Integer.valueOf(6), Par.run(s, sum(List.list(1, 2, 3))).get());
      assertEquals(Integer.valueOf(10), Par.run(s, sum(List.list(1, 2, 3, 4))).get());
      assertEquals(Integer.valueOf(15), Par.run(s, sum(List.list(1, 2, 3, 4, 5))).get());
    } catch (InterruptedException | ExecutionException e) {
      fail();
    }
  }

  public static Par<Integer> sum(List<Integer> ints) {
    if (ints.length() <= 1) {
      return Par.unit(() -> ints.headOption().getOrElse(0));
    } else {
      final Tuple<List<Integer>, List<Integer>> tuple = ints.splitAt(ints.length() / 2);
      return Par.map2(Par.fork(() -> sum(tuple._1)), Par.fork(() -> sum(tuple._2)), x -> y -> x + y);
    }
  }

}
