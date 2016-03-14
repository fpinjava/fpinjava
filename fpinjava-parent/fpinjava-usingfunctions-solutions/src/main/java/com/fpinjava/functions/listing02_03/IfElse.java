package com.fpinjava.functions.listing02_03;


import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Result;
import com.fpinjava.common.Tuple;

public class IfElse {

  public <T> T ifElse(List<Boolean> conditions, List<T> ifTrue, T ifFalse) {

    Function<Tuple<Boolean, T>, Boolean> f1 =
        new Function<Tuple<Boolean, T>, Boolean>() {
          public Boolean apply(Tuple<Boolean, T> y) {
            return y._1;
          }
        };

    Function<List<Tuple<Boolean, T>>, Result<Tuple<Boolean, T>>> f2 =
        new Function<List<Tuple<Boolean, T>>, Result<Tuple<Boolean, T>>>() {
          public Result<Tuple<Boolean, T>> apply(List<Tuple<Boolean, T>> x) {
            return x.first(f1);
          }
        };

    Function<Tuple<Boolean, T>, T> f3 =
        new Function<Tuple<Boolean, T>, T>() {
          public T apply(Tuple<Boolean, T> x) {
            return x._2;
          }
        };

    Result<List<Tuple<Boolean, T>>> temp1 = conditions.zip(ifTrue);
    Result<Tuple<Boolean, T>> temp2 = temp1.flatMap(f2);
    Result<T> temp3 = temp2.map(f3);
    T result = temp3.getOrElse(ifFalse);
    return result;
  }

}
