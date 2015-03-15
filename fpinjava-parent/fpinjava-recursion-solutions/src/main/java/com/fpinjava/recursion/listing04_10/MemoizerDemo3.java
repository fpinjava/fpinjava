package com.fpinjava.recursion.listing04_10;

import com.fpinjava.common.Function;
import com.fpinjava.recursion.listing04_06.Memoizer;
import com.fpinjava.recursion.listing04_09.Tuple3;

public class MemoizerDemo3 {

  public static void main(String[] args) {
    automaticMemoizationExample3();
  }

  private static Integer longCalculation(Integer x) {
    try {
      Thread.sleep(1_000);
    } catch (InterruptedException ignored) {
    }
    return x * 2;
  }

  public static Function<Tuple3<Integer, Integer, Integer>, Integer> ft = x -> longCalculation(x._1) + longCalculation(x._2) - longCalculation(x._3);
  public static Function<Tuple3<Integer, Integer, Integer>, Integer> ftm = Memoizer.memoize(ft);

  public static void automaticMemoizationExample3() {
    long startTime = System.currentTimeMillis();
    Integer result1 = ftm.apply(new Tuple3<>(2, 3, 4));
    long time1 = System.currentTimeMillis() - startTime;
    startTime = System.currentTimeMillis();
    Integer result2 = ftm.apply(new Tuple3<>(2, 3, 4));
    long time2 = System.currentTimeMillis() - startTime;
    System.out.println(result1);
    System.out.println(result2);
    System.out.println(time1);
    System.out.println(time2);
  }
}
