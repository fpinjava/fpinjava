package com.fpinjava.functionalstate.exercise08_11;

public interface Input {
  Type type();
  boolean isCoin();
  boolean isTurn();

  enum Type {COIN,TURN}
}
