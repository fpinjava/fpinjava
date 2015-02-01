package com.fpinjava.recursion.corecursion;

import java.math.BigInteger;
import java.util.List;

import static com.fpinjava.common.CollectionUtilities.*;
import com.fpinjava.common.Function;
import com.fpinjava.common.Tuple;
import com.fpinjava.recursion.exercise04_09.Memoization;

public class Corecursion {

  public static String fiboCorecursive(int number) {
    Tuple<BigInteger, BigInteger> seed = new Tuple<>(BigInteger.ZERO, BigInteger.ONE);
    Function<Tuple<BigInteger, BigInteger>,Tuple<BigInteger, BigInteger>> f = x -> new Tuple<>(x._2, x._1.add(x._2));
    List<BigInteger> list = map(iterate(seed, f, number + 1), x -> x._1);
    return Memoization.makeString(list, ", ");
   }
}
