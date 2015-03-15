package com.fpinjava.functionalstate.exercise08_11;

public class Turn implements Input {

  @Override
  public Type type() {
    return Input.Type.TURN;
  }

  @Override
  public boolean isCoin() {
    return false;
  }

  @Override
  public boolean isTurn() {
    return true;
  }
}

