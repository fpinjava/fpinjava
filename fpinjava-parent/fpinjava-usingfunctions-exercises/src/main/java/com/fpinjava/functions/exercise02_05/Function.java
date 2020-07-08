package com.fpinjava.functions.exercise02_05;

public interface Function<T, U> {

  U apply(T arg);



  static <T, U, V> Function<Function<U, V>, Function<Function<T, U>, Function<T, V>>> higherCompose(){
    return x -> y -> z -> x.apply(y.apply(z));
  }

//  static <T, U, V> Function<> higherCompose2() {
//    return x -> y -> z -> x.apply(y.apply(z));
//  }
//
//  static <T, U, V> Function<T, Function<>> higherCompose2() {
//    return x -> y -> z -> x.apply(y.apply(z));
//  }
//
//  static <T, U, V> Function<Function<>, Function<>> higherCompose2() {
//    return x -> y -> z -> x.apply(y.apply(z));
//  }
//
//  static <T, U, V> Function<Function<>, Function<Function<>, Function<>>> higherCompose2() {
//    return x -> y -> z -> x.apply(y.apply(z));
//  }
}
