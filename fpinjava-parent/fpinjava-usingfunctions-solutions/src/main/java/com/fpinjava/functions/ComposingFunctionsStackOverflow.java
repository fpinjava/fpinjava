package com.fpinjava.functions;

import com.fpinjava.functions.listing02_02.Function;

public class ComposingFunctionsStackOverflow {

  public static int fnum = 10_000;
  public static Function<Integer, Integer> g = x -> x;
  public static Function<Integer, Integer> f = x -> x + 1;
  static {
    for (int i = 0; i < fnum; i++) {
      g = g.compose(f);
    }
  }
}
