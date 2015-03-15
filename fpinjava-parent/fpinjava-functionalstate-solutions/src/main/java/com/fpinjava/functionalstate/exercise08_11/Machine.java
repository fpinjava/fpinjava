package com.fpinjava.functionalstate.exercise08_11;

import com.fpinjava.common.Tuple3;

public class Machine {

  public final boolean locked;
  public final int candies;
  public final int coins;

  public Machine(boolean locked, int candies, int coins) {
    this.locked = locked;
    this.candies = candies;
    this.coins = coins;
  }
  
  public Tuple3<Boolean, Integer, Integer> value() {
    return new Tuple3<>(this.locked, this.candies, this.coins);
  }
}
