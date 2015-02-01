package com.fpinjava.recursion.exercise04_02;

import java.math.BigInteger;

import com.fpinjava.recursion.listing04_03.TailCall;
import static com.fpinjava.recursion.listing04_03.TailCall.*;

public class Fibonacci {

  public static BigInteger fib(int x) {
    return fib_(BigInteger.ONE, BigInteger.ZERO, BigInteger.valueOf(x)).eval();
  }

  private static TailCall<BigInteger> fib_(BigInteger acc1, BigInteger acc2, BigInteger x) {
    if (x.equals(BigInteger.ZERO)) {
      return ret(BigInteger.ZERO);
    } else if (x.equals(BigInteger.ONE)) {
      return ret(acc1.add(acc2));
    } else {
      return sus(() -> fib_(acc2, acc1.add(acc2), x.subtract(BigInteger.ONE)));
    }
  }
}
