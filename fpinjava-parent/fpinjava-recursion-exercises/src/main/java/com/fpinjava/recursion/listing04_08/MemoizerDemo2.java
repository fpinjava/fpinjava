package com.fpinjava.recursion.listing04_08;

import com.fpinjava.common.Function;
import com.fpinjava.recursion.listing04_06.Memoizer;

public class MemoizerDemo2 {

  public static void main(String[] args) {
    automaticMemoizationExample2();
  }

  private static Integer longCalculation(Integer x) {
    try {
      Thread.sleep(1_000);
    } catch (InterruptedException ignored) {
    }
    return x * 2;
  }

  private static Function<Integer, Function<Integer, Function<Integer, Integer>>> f3m = 
      Memoizer.memoize(x -> Memoizer.memoize(y -> Memoizer.memoize(z -> longCalculation(x) + longCalculation(y) - longCalculation(z))));

  public static void automaticMemoizationExample2() {
    long startTime = System.currentTimeMillis();
    Integer result1 = f3m.apply(2).apply(3).apply(4);
    long time1 = System.currentTimeMillis() - startTime;
    startTime = System.currentTimeMillis();
    Integer result2 = f3m.apply(2).apply(3).apply(4);
    long time2 = System.currentTimeMillis() - startTime;
    System.out.println(result1);
    System.out.println(result2);
    System.out.println(time1);
    System.out.println(time2);
  }
}
