package com.fpinjava.state.exercise12_10;


public class Withdraw implements Input {

  private final int amount;

  public Withdraw(int amount) {
    super();
    this.amount = amount;
  }

  @Override
  public Type type() {
    return Type.WITHDRAW;
  }

  @Override
  public boolean isDeposit() {
    return false;
  }

  @Override
  public boolean isWithdraw() {
    return true;
  }

  @Override
  public int getAmount() {
    return this.amount;
  }
}
