package com.fpinjava.functions.exercise02_03;


public class FunctionExamples {

  public static final Function<Integer, Function<Integer, Integer>> add = new Function<Integer, Function<Integer, Integer>>() {
    @Override
    public Function<Integer, Integer> apply(Integer x) {
      return new Function<Integer, Integer>() {
        @Override
        public Integer apply(Integer y) {
          return x + y;
        }
      };
    }
  };

  public static final BinaryOperator add2 =  x -> y -> x + y; // To be implemented

  public static final BinaryOperator mult =  x -> y -> x * y; // To be implemented
}
