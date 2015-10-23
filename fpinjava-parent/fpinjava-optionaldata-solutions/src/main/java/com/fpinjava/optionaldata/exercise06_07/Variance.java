package com.fpinjava.optionaldata.exercise06_07;


import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.optionaldata.exercise06_06.Option;

public class Variance {

  static Function<List<Double>, Double> sum = ds -> ds.foldLeft(0.0, a -> b -> a + b);

  static Function<List<Double>, Option<Double>> mean =
      ds -> ds.isEmpty()
          ? Option.none()
          : Option.some(sum.apply(ds) / ds.length());

  static Function<List<Double>, Option<Double>> variance =
      ds -> mean.apply(ds).flatMap(m -> mean.apply(ds.map(x -> Math.pow(x - m, 2))));
}

