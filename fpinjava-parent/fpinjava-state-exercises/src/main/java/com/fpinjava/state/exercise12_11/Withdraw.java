package com.fpinjava.state.exercise12_11;


public class Withdraw implements Input {
  @Override
  public Type type() {
    return null;
  }

  @Override
  public boolean isDeposit() {
    return false;
  }

  @Override
  public boolean isWithdraw() {
    return false;
  }
}
