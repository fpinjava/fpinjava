package com.fpinjava.functionalstate.exercise08_11;

public class Coin implements Input {

  @Override
  public Type type() {
    return Input.Type.COIN;
  }

  @Override
  public boolean isCoin() {
    return true;
  }

  @Override
  public boolean isTurn() {
    return false;
  }
}
