package com.fpinjava.advancedlisthandling.exercise08_23;

import com.fpinjava.common.Result;
import org.junit.Test;

import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;


public class ListTest {

  @Test
  public void testParFoldLeft() throws Exception {
    int testLimit = 35000;
    int numberOfThreads = 8;

    List<Long> testList = SimpleRNG.doubles(testLimit, new SimpleRNG.Simple(1))._1.map(x -> (long) (x * 30));
    ExecutorService es = Executors.newFixedThreadPool(numberOfThreads);

    Result<BigInteger> result = testList.parFoldLeft(es, BigInteger.ZERO, a-> b -> a.add(BigInteger.valueOf(fibo(b))), a -> a::add);
    assertTrue(result.map(r -> r.longValue() == 1552643551L).getOrElse(false));
    es.shutdown();
  }

  private static long fibo(long x) {
    return x == 0
        ? 0
        : x == 1
            ? 1
            : fibo(x - 1) + fibo(x - 2);
  }

}
