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
    return Type.DEPOSIT;
  }

  @Override
  public boolean isDeposit() {
    return true;
  }

  @Override
  public boolean isWithdraw() {
    return false;
  }

  @Override
  public Result<Integer> getAmount() {
    return this.amount;
  }
}
