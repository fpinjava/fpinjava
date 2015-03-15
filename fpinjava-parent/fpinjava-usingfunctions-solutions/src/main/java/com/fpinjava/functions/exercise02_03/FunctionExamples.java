package com.fpinjava.functions.exercise02_03;

import com.fpinjava.functions.listing02_02.Function;

public class FunctionExamples {

  public static final Function<Integer, Function<Integer, Integer>> add = x -> y -> x + y;
  
  public static final BinaryOperator add2 = x -> y -> x + y;
  
  public static final BinaryOperator mult = x -> y -> x * y;
}
