package com.fpinjava.errorhandling.listing06_04;

import com.fpinjava.common.Function;
import com.fpinjava.common.Supplier;
import com.fpinjava.errorhandling.exercise06_01.Option;

import static com.fpinjava.errorhandling.exercise06_01.Option.*;

public class Insurance {

  public static Option<Double> parseInsuranceRateQuote(String age, String numberOfSpeedingTickets) {
    Option<Integer> optAge = validate(() -> Integer.valueOf(age));
    Option<Integer> optTickets = 
    validate(() -> Integer.valueOf(numberOfSpeedingTickets));
    //return insuranceRateQuote.apply(optAge).apply(optTickets);
    return null; // Line above does not type check!
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
