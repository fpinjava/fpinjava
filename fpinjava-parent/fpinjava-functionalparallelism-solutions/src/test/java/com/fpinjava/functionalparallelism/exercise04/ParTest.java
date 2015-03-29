package com.fpinjava.functionalparallelism.exercise04;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.fpinjava.common.Function;


public class ParTest {

  Function<Integer, Integer> fac = x -> x <= 1
      ? x
      : x * this.fac.apply(x -1);

  @Test
  public void testAsyncF() {
    ExecutorService s = Executors.newCachedThreadPool();
    try {
      assertEquals(fac.apply(25), Par.run(s, Par.asyncF(fac).apply(25)).get());
    } catch (InterruptedException | ExecutionException e) {
      fail();
    }
  }

}
