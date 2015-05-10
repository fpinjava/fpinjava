package com.fpinjava.errorhandling.exercise06_07;

import com.fpinjava.common.Function;
import com.fpinjava.common.Supplier;


public class Insurance {

  public static Either<Exception, Double> parseInsuranceRateQuote(String age, String numberOfSpeedingTickets) {
    Either<Exception, Integer> optAge = validate(() -> Integer.valueOf(age));
    Either<Exception, Integer> optTickets = validate(() -> Integer
        .parseInt(numberOfSpeedingTickets));
    return optAge.map2(optTickets, insuranceRateQuote);
  }

public static <A> Either<Exception, A> validate(Supplier<A> a) {
  try {
    return Either.right(a.get());
  } catch (Exception e) {
    return Either.left(e);
  }
}

  public static Function<Integer, Function<Integer, Double>> insuranceRateQuote = x -> y -> (double) (x - y) / 2;
}
