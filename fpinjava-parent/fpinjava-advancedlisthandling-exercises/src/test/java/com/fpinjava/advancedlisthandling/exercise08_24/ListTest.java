package com.fpinjava.advancedlisthandling.exercise08_24;

import com.fpinjava.common.Function;
import com.fpinjava.common.Result;
import org.junit.Test;

import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;


public class ListTest {

  @Test
  public void testParMap() throws Exception {
    int testLimit = 35000;
    int numberOfThreads = 4;

    List<Long> testList = SimpleRNG.doubles(testLimit, new SimpleRNG.Simple(1))._1.map(x -> (long) (x * 30));

    ExecutorService es = Executors.newFixedThreadPool(numberOfThreads);

    Function<Long, Long> f = ListTest::fibo;

    Function<BigInteger, Function<Long, BigInteger>> g = x -> y -> x.add(BigInteger.valueOf(y));

    Result<List<Long>> result = testList.parMap(es, f);
    assertTrue(result.map(r -> r.foldLeft(BigInteger.ZERO, g).longValue() == 1552643551L).getOrElse(false));

    es.shutdown();
  }

  private static long fibo(long x) {
    if (x == 30) System.out.println(x);
    return x == 0
        ? 0
        : x == 1
            ? 1
            : fibo(x - 1) + fibo(x - 2);
  };
}
