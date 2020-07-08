package com.fpinjava.functions.exercise02_03;


public class FunctionExamples {

  //why does x is highlighted by intelliJ as purple with under score?
  public static final Function<Integer, Function<Integer, Integer>> add = x -> y -> x + y;

  public static final BinaryOperator add2 =  x -> y -> x + y;

  public static final BinaryOperator mult =  x -> y -> x * y;
}
