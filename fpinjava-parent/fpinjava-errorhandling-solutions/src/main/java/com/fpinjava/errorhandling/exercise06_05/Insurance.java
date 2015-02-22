package com.fpinjava.errorhandling.exercise06_05;

import com.fpinjava.common.Function;
import com.fpinjava.common.Supplier;

import static com.fpinjava.errorhandling.exercise06_05.Option.*;

public class Insurance {

  public static Option<Double> parseInsuranceRateQuote(String age, String numberOfSpeedingTickets) {
    Option<Integer> optAge = validate(() -> Integer.valueOf(age));
    Option<Integer> optTickets = validate(() -> Integer.parseInt(numberOfSpeedingTickets));
    return map2(optAge, optTickets, insuranceRateQuote);
  }

  public static <A> Option<A> validate(Supplier<A> a) {
    try {
      return some(a.get());
    } catch (Exception e) {
      return none();
    }
  }

  public static Function<Integer, Function<Integer, Double>> insuranceRateQuote = x -> y -> (double) (x - y) / 2;
}
