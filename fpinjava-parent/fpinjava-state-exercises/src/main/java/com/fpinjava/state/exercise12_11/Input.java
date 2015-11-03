package com.fpinjava.state.exercise12_11;


public interface Input {

  Type type();
  boolean isDeposit();
  boolean isWithdraw();
  // ??? getAmount()

  enum Type {DEPOSIT,WITHDRAW}
}
