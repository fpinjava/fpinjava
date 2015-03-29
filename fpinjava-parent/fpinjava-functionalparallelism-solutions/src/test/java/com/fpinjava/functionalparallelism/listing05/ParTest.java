package com.fpinjava.functionalparallelism.listing05;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.fpinjava.common.List;
import com.fpinjava.common.Tuple;


public class ParTest {

  @Test
  public void testSum_() {
    assertEquals(Integer.valueOf(0), sum(List.list()));
    assertEquals(Integer.valueOf(1), sum(List.list(1)));
    assertEquals(Integer.valueOf(3), sum(List.list(1, 2)));
    assertEquals(Integer.valueOf(6), sum(List.list(1, 2, 3)));
    assertEquals(Integer.valueOf(10), sum(List.list(1, 2, 3, 4)));
    assertEquals(Integer.valueOf(15), sum(List.list(1, 2, 3, 4, 5)));
  }

  public static Integer sum(List<Integer> ints) {
    if (ints.length() <= 1) {
      return ints.headOption().getOrElse(0);
    } else {
      final Tuple<List<Integer>, List<Integer>> tuple = ints.splitAt(ints.length() / 2);
      try {
        return Par.run(Executors.newCachedThreadPool(), Par.map2(Par.unit(() -> sum(tuple._1)), Par.unit(() -> sum(tuple._2)), x -> y -> x + y)).get();
      } catch (InterruptedException | ExecutionException e) {
        throw new IllegalStateException(e);
      }
    }
  }

}
