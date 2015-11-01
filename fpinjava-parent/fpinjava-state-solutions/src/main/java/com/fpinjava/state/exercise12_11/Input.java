package com.fpinjava.state.exercise12_11;


import com.fpinjava.common.Result;

public interface Input {

  Type type();
  boolean isDeposit();
  boolean isWithdraw();
  Result<Integer> getAmount();

  enum Type {DEPOSIT,WITHDRAW}
}
