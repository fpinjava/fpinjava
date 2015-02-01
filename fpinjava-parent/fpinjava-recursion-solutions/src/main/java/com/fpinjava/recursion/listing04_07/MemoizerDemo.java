package com.fpinjava.recursion.listing04_07;

import com.fpinjava.common.Function;
import com.fpinjava.recursion.listing04_06.Memoizer;

public class MemoizerDemo {

  public static void main(String[] args) {
    automaticMemoizationExample();
  }

  private static Integer longCalculation(Integer x) {
    try {
      Thread.sleep(1_000);
    } catch (InterruptedException ignored) {
    }
    return x * 2;
  }

  private static Function<Integer, Integer> f = MemoizerDemo::longCalculation;
  private static Function<Integer, Integer> g = Memoizer.memoize(f);

  public static void automaticMemoizationExample() {
    long startTime = System.currentTimeMillis();
    Integer result1 = g.apply(1);
    long time1 = System.currentTimeMillis() - startTime;
    startTime = System.currentTimeMillis();
    Integer result2 = g.apply(1);
    long time2 = System.currentTimeMillis() - startTime;
    System.out.println(result1);
    System.out.println(result2);
    System.out.println(time1);
    System.out.println(time2);
  }
}
