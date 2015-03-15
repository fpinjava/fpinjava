package com.fpinjava.recursion.exercise04_09;

import java.math.BigInteger;
import java.util.List;

import static com.fpinjava.common.CollectionUtilities.*;
import com.fpinjava.recursion.listing04_03.TailCall;
import static com.fpinjava.recursion.listing04_03.TailCall.*;

public class Memoization {

  public static String fibo(int number) {
    List<BigInteger> list = fibo_(list(BigInteger.ZERO), BigInteger.ONE, BigInteger.ZERO, BigInteger.valueOf(number)).eval();
    return makeString(list, ", ");
  }

  private static <T> TailCall<List<BigInteger>> fibo_(List<BigInteger> acc, BigInteger acc1, BigInteger acc2, BigInteger x) {
    return x.equals(BigInteger.ZERO)
        ? ret(acc)
        : x.equals(BigInteger.ONE)
            ? ret(append(acc, acc1.add(acc2)))
            : sus(() -> fibo_(append(acc, acc1.add(acc2)), acc2, acc1.add(acc2), x.subtract(BigInteger.ONE)));
  }

  public static <T> String makeString(List<T> list, String separator) {
    return list.isEmpty()
        ? ""
        : tail(list).isEmpty()
            ? head(list).toString()
            : head(list) + foldLeft(tail(list), "", x -> y -> x + separator + y);
  }
}
