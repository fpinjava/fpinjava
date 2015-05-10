package com.fpinjava.propertybasedtesting.listing02;

import com.fpinjava.common.Function;
import com.fpinjava.common.Tuple;
import com.fpinjava.state.RNG;

public class Prop {

  public final Function<Tuple<TestCases, RNG>, Result> run;

  public Prop(Function<Tuple<TestCases, RNG>, Result> run) {
    super();
    this.run = run;
  }
  
  public static class TestCases {

    public final int value;

    public TestCases(int value) {
      super();
      this.value = value;
    }
  }

  public static abstract class Result {
    protected abstract boolean isFalsified();
  }
  
  public static class Passed extends Result {
    public boolean isFalsified() {
      return false;
    }
  }

  public static class Falsified extends Result {
  
    public final FailedCase failure;
    public final SuccessCount success;
  
    public Falsified(FailedCase failure, SuccessCount success) {
      super();
      this.failure = failure;
      this.success = success;
    }
  
    public boolean isFalsified() {
      return true;
    }
  }
  
  public static class SuccessCount {
  
    public final int value;
  
    public SuccessCount(int value) {
      super();
      this.value = value;
    }
  }
  
  public static class FailedCase {
  
    public final String value;
  
    public FailedCase(String value) {
      super();
      this.value = value;
    }
  }
}