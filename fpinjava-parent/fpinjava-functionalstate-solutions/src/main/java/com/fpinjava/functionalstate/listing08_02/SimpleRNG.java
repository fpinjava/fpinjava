package com.fpinjava.functionalstate.listing08_02;


import com.fpinjava.common.Tuple;

public class SimpleRNG implements RNG {

  private final long seed;
  
  public SimpleRNG(long seed) {
    super();
    this.seed = seed;
  }

  @Override
  public Tuple<Integer, RNG> nextInt() {
    final long newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL;
    final RNG nextRng = new SimpleRNG(newSeed);
    int n = (int) (newSeed >>> 16);
    return new Tuple<>(n, nextRng);
  }
  
  @Override
  public String toString() {
    return String.format("SimpleRNG(%s)", seed);
  }
  
  public static void main(String[] args) {
    RNG rng = new SimpleRNG(42);
    Tuple<Integer, RNG> t1 = rng.nextInt();
    System.out.println(t1);
    Tuple<Integer, RNG> t2 = t1._2.nextInt();
    System.out.println(t2);
  }
}
