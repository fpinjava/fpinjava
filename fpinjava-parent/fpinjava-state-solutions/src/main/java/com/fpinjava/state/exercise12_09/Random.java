package com.fpinjava.state.exercise12_09;

import com.fpinjava.common.Function;
import com.fpinjava.common.Tuple;

public class Random<A> extends State<RNG, A> {

  public Random(Function<RNG, Tuple<A, RNG>> run) {
    super(run);
  }

  public static State<RNG, Integer> intRnd = new Random<>(RNG::nextInt);

  public static State<RNG, Boolean> booleanRnd = intRnd.map(x -> x % 2 == 0);

  public static State<RNG, Tuple<Integer, Integer>> intPairRnd = intRnd.map2(intRnd, (Integer x) -> (Integer y) -> new Tuple<>(x, y));

  public static State<RNG, Integer> notMultipleOfFiveRnd = intRnd.flatMap(x -> {
    int mod = x % 5;
    return mod != 0
        ? unit(x)
        : Random.notMultipleOfFiveRnd;
  });

}
