package com.fpinjava.functionalstate.exercise08_11;

public interface Input {
  Type type();
  boolean isCoin();
  boolean isTurn();
  
  public static enum Type {COIN,TURN}
}