package com.fpinjava.functionalparallelism.exercise06;

import static org.junit.Assert.*;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Tuple;


public class ParTest {

  private static List<Integer> list = List.range(0, 1000);

  private Random random = new Random();
  /*
   * We should not test the resulting list order, because is may not be constant, due to parallelism.
   * So, we fold the list and test the result. Of course, this only works if the operation used for
   * folding is commutative.
   */
  @Test
  public void testParFilter() {
    ExecutorService s = Executors.newCachedThreadPool();
    Function<Integer, Boolean> f =  x -> {
      try {
        Thread.sleep(random.nextInt(1000 - 10) + 10L);
      } catch (InterruptedException e) {
        fail();
      }
      return x % 2 == 0;
    };
    try {
      assertEquals(Integer.valueOf(0), Par.run(s, sum(Par.run(s, Par.parFilter(List.<Integer> list(), f)).get())).get());
      assertEquals(Integer.valueOf(0), Par.run(s, sum(Par.run(s, Par.parFilter(List.list(1), f)).get())).get());
      assertEquals(Integer.valueOf(2), Par.run(s, sum(Par.run(s, Par.parFilter(List.list(1, 2), f)).get())).get());
      assertEquals(Integer.valueOf(2), Par.run(s, sum(Par.run(s, Par.parFilter(List.list(1, 2, 3), f)).get())).get());
      assertEquals(Integer.valueOf(6), Par.run(s, sum(Par.run(s, Par.parFilter(List.list(1, 2, 3, 4), f)).get())).get());
      assertEquals(Integer.valueOf(6), Par.run(s, sum(Par.run(s, Par.parFilter(List.list(1, 2, 3, 4, 5), f)).get())).get());
      assertEquals(Integer.valueOf(12), Par.run(s, sum(Par.run(s, Par.parFilter(List.list(1, 2, 3, 4, 5, 6), f)).get())).get());
      assertEquals(Integer.valueOf(12), Par.run(s, sum(Par.run(s, Par.parFilter(List.list(1, 2, 3, 4, 5, 6, 7), f)).get())).get());
      assertEquals(Integer.valueOf(20), Par.run(s, sum(Par.run(s, Par.parFilter(List.list(1, 2, 3, 4, 5, 6, 7, 8), f)).get())).get());
      assertEquals(Integer.valueOf(249500), Par.run(s, sum(Par.run(s, Par.parFilter(list, f)).get())).get());
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
