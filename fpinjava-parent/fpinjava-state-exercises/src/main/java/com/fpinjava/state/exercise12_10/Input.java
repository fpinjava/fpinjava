package com.fpinjava.state.exercise12_10;


public interface Input {

  Type type();
  boolean isDeposit();
  boolean isWithdraw();
  int getAmount();

  enum Type {DEPOSIT,WITHDRAW}
}
