package com.fpinjava.functionaparallelism.exercise02;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.fpinjava.common.List;


public class ParTest {

  @Test
  public void testSum() {
    ExecutorService s = Executors.newCachedThreadPool();
    try {
      assertEquals(Integer.valueOf(0), Par.run(s, Par.sum(List.list())).get());
      assertEquals(Integer.valueOf(1), Par.run(s, Par.sum(List.list(1))).get());
      assertEquals(Integer.valueOf(3), Par.run(s, Par.sum(List.list(1, 2))).get());
      assertEquals(Integer.valueOf(6), Par.run(s, Par.sum(List.list(1, 2, 3))).get());
      assertEquals(Integer.valueOf(10), Par.run(s, Par.sum(List.list(1, 2, 3, 4))).get());
      assertEquals(Integer.valueOf(15), Par.run(s, Par.sum(List.list(1, 2, 3, 4, 5))).get());
    } catch (InterruptedException | ExecutionException e) {
      fail();
    }
  }

}
