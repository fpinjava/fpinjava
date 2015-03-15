package com.fpinjava.functionalstate.exercise08_03;

import java.util.Random;

import com.fpinjava.common.Tuple;
import com.fpinjava.common.Tuple3;

public class SimpleRNG {

  public static int rollDie(Random rng)  {
    return rng.nextInt(6);
  }
  
  public static Tuple<Integer, RNG> nonNegativeInt(RNG rng) {
    Tuple<Integer, RNG> t = rng.nextInt();
    return new Tuple<>((t._1 < 0) ? -(t._1 + 1) : t._1, t._2);
  }
  
  /*
   * We generate an integer >= 0 and divide it by one higher than the
   * maximum. This is just one possible solution.
   */
   public static Tuple<Double, RNG> doubleRnd(RNG rng) {
     Tuple<Integer, RNG> t = nonNegativeInt(rng);
     return new Tuple<>(t._1 / ((double) Integer.MAX_VALUE + 1), t._2);
   }

   public static Tuple<Tuple<Integer, Double>, RNG> intDouble(RNG rng) {
     Tuple<Integer, RNG> t1 = rng.nextInt();
     Tuple<Double, RNG> t2 = doubleRnd(t1._2);
     return new Tuple<>(new Tuple<>(t1._1, t2._1), t2._2);
   }

   public static Tuple<Tuple<Double, Integer>, RNG> doubleInt(RNG rng) {
     Tuple<Tuple<Integer, Double>, RNG> t = intDouble(rng);
     return new Tuple<>(new Tuple<>(t._1._2, t._1._1), t._2);
   }

   public static Tuple<Tuple3<Double, Double, Double>, RNG> double3(RNG rng) {
     Tuple<Double, RNG> t1 = doubleRnd(rng);
     Tuple<Double, RNG> t2 = doubleRnd(t1._2);
     Tuple<Double, RNG> t3 = doubleRnd(t2._2);
     return new Tuple<>(new Tuple3<>(t1._1, t2._1, t3._1), t3._2);
   }
}
