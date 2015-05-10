package com.fpinjava.propertybasedtesting.listing06;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.fpinjava.common.Function;
import com.fpinjava.parallelism.Par;

public class PropTest {

  @Test
  public void test() {
    ExecutorService es = Executors.newCachedThreadPool();
    Gen<Par<Integer>> g = Gen.unit(() -> Par.unit(() ->1));
    Function<Par<Integer>, Boolean> f = pi -> {
      try {
        return Par.map(pi, x -> x + 1).apply(es).get().equals(Par.unit(() -> 2).apply(es).get());
      } catch (InterruptedException | ExecutionException e) {
        return false;
      }
    };
    Prop p = Prop.forAll(g, f);
    assertEquals("+ OK, passed 100 tests.", p.run());
  }

  @Test
  public void test2() {
    assertEquals("+ OK, proved property.", Prop.check(() -> true).run());
  }
  
  @Test
  public void test3() {
    ExecutorService es = Executors.newCachedThreadPool();
    Prop p = Prop.check(() -> {
      Par<Integer> p1 = Par.map(Par.unit(() -> 1), x -> x + 1); 
      Par<Integer> p2 = Par.unit(() -> 2);
      try {
        return p1.apply(es).get().equals(p2.apply(es).get());
      } catch (Exception e) {
        return false;
      }
    });
    assertEquals("+ OK, proved property.", p.run());
  }
  
  @Test
  public void test4() {
    ExecutorService es = Executors.newCachedThreadPool();
    Prop p = Prop.check(() -> {
      try {
        return Prop.equal(Par.map(Par.unit(() -> 1), x -> x + 1), Par.unit(() -> 2)).apply(es).get();
      } catch (Exception e) {
        return false;
      }
    });
    assertEquals("+ OK, proved property.", p.run());
  }
}
