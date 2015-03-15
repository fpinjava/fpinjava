package com.fpinjava.errorhandling.mean;

import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.errorhandling.exercise06_05.Option;

public class Mean {

  static Function<List<Integer>, Double> mean = xs -> {
    if (xs.isEmpty()) {
      throw new ArithmeticException("mean of empty list!");
    } else {
      return xs.foldLeft(0.0, x -> y -> x + y) / xs.length();
    }
  };
  
  static Function<List<Integer>, Double> mean_1 = xs ->
    xs.isEmpty()
        ? Double.NaN
        : xs.foldLeft(0.0, x -> y -> x + y) / xs.length();
    
  static Function<Double, Function<List<Integer>, Double>> mean_2 =
      onEmpty -> xs -> xs.isEmpty()
          ? onEmpty
          : xs.foldLeft(0.0, x -> y -> x + y) / xs.length();
      
  static Function<List<Integer>, Double> mean_3 = mean_2.apply(Double.NaN);
  
  static Function<List<Integer>, Option<Double>> meanOption = xs -> 
      xs.isEmpty()
         ? Option.none()
         : Option.some(xs.foldLeft(0.0, x -> y -> x + y) / xs.length());
}
