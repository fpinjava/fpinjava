package com.fpinjava.io.listing13_01;


import com.fpinjava.common.Effect;
import com.fpinjava.common.Function;
import com.fpinjava.common.Result;

public class ResultTest {

  public static void main(String... args) {

    Result<Integer> ra = Result.success(4);

    Result<Integer> rb = Result.success(0);

    Function<Integer, Result<Double>> inverse = x -> x != 0
        ? Result.success((double) 1 / x)
        : Result.failure("Division by 0");

    Effect<Double> print = System.out::println;

    Result<Double> rt1 = ra.flatMap(inverse);
    Result<Double> rt2 = rb.flatMap(inverse);

    System.out.print("Inverse of 4: ");
    rt1.forEach(print);

    System.out.print("Inverse of 0: ");
    rt2.forEach(print);
  }
}
