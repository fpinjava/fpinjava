package com.fpinjava.io.exercise13_02;


import com.fpinjava.common.Function;

public class ResultTest {

  public static void main(String... args) {

    Result<Integer> ra = Result.success(4);
    Result<Integer> rb = Result.success(0);

    Function<Integer, Result<Double>> inverse = x -> x != 0
        ? Result.success((double) 1 / x)
        : Result.failure("Division by 0");

    Result<Double> rt1 = ra.flatMap(inverse);
    Result<Double> rt2 = rb.flatMap(inverse);

    System.out.print("Inverse of 4: ");
    rt1.forEachOrFail(System.out::println).forEach(ResultTest::log);

    System.out.print("Inverse of 0: ");
    rt2.forEachOrFail(System.out::println).forEach(ResultTest::log);
  }

  private static void log(String s) {
    System.out.println(s);
  }
}
