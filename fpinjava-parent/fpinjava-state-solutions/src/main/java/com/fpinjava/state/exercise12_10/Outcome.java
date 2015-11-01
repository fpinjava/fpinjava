package com.fpinjava.state.exercise12_10;


import com.fpinjava.common.List;

/**
 * This class represent the result that is returned by the state machine.
 * It is not mandatory to use such a class, but it is much clearer than
 * using tuples.
 */
public class Outcome {

  public final Integer account;
  public final List<Integer> operations;

  public Outcome(Integer account, List<Integer> operations) {
    super();
    this.account = account;
    this.operations = operations;
  }

  public String toString() {
    return "(" + account.toString() + "," + operations.toString() + ")";
  }
}
