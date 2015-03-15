package com.fpinjava.functionalstate.exercise08_10;

import com.fpinjava.common.Tuple;


public class Simple implements RNG {

  private final long seed;

  public Simple(long seed) {
    super();
    this.seed = seed;
  }

  @Override
  public Tuple<Integer, RNG> nextInt() {
    
    /*
     *  `&` is bitwise AND. We use the current seed to generate a new seed.
     */
    final long newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL;
    /*
     *  The next state, which is an `RNG` instance created from the new seed.
     */
    final RNG nextRNG = new Simple(newSeed);
    /*
     *  `>>>` is right binary shift with zero fill. The value `n` is our new pseudo-random integer.
     */
    final int  n = (int) (newSeed >>> 16);
    /*
     *  The return value is a tuple containing both a pseudo-random integer and the next `RNG` state.
     */
    return new Tuple<>(n, nextRNG);
  }
}
