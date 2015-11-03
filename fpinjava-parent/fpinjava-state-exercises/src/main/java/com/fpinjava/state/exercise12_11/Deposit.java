package com.fpinjava.state.exercise12_11;



public class Deposit implements Input {

  @Override
  public Type type() {
    throw new IllegalStateException("To be implemented");
  }

  @Override
  public boolean isDeposit() {
    throw new IllegalStateException("To be implemented");
  }

  @Override
  public boolean isWithdraw() {
    throw new IllegalStateException("To be implemented");
  }
}
