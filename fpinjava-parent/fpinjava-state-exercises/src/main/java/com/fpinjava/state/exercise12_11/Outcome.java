package com.fpinjava.state.exercise12_11;


import com.fpinjava.common.List;
import com.fpinjava.common.Result;

public class Outcome {

  public final Integer account;
  public final List<Result<Integer>> operations;

  public Outcome(Integer account, List<Result<Integer>> operations) {
    super();
    this.account = account;
    this.operations = operations;
  }

}
