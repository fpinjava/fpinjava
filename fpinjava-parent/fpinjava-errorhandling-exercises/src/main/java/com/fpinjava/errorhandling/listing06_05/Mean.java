package com.fpinjava.errorhandling.listing06_05;

import com.fpinjava.common.List;
import com.fpinjava.common.Supplier;

public class Mean {

  public static Either<String, Double> mean(List<Integer> xs) {
    return xs.isEmpty()
        ? Either.left("Mean of empty list!")
        : Either.right(xs.foldLeft(0.0, x -> y -> x + y) / xs.length());
  }
  
  public static Either<Exception, Integer> safeDiv(Integer x, Integer y) {
    try {
      return Either.right(x / y);
    } catch (Exception e) {
      return Either.left(e);
    }
  }
  
  public static <A> Either<Exception, A> validate(Supplier<A> a) {
    try {
      return Either.right(a.get());
    } catch (Exception e) {
      return Either.left(e);
    }
  }
}
