package com.fpinjava.state.exercise12_11;


import com.fpinjava.common.Result;

public class Deposit implements Input {

  private final Result<Integer> amount;

  public Deposit(Result<Integer> amount) {
    super();
    this.amount = amount;
  }

  public Deposit(Integer amount) {
    super();
    this.amount = Result.success(amount);
  }

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
