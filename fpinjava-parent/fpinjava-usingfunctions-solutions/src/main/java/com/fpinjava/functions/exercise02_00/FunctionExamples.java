package com.fpinjava.functions.exercise02_00;

public class FunctionExamples {

  public static final Function triple = new Function() {

    @Override
    public int apply(int arg) {
      return arg * 3;
    }
  };

  public static final Function square = new Function() {

    @Override
    public int apply(int arg) {
      return arg * arg;
    }
  };

  public static final Function compose(final Function f1, final Function f2) {
    return new Function() {

      @Override
      public int apply(int arg) {
        return f1.apply(f2.apply(arg));
      }
    };
  }
}
