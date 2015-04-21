package com.fpinjava.propertybasedtesting.exercise04;

import com.fpinjava.common.Either;
import com.fpinjava.common.Function;
import com.fpinjava.common.Tuple;

public interface Prop {

  public Either<Tuple<FailedCase, SuccessCount>, SuccessCount> check();

  default Prop and(Prop p) {
    return new Prop() {
  
      @Override
      public Either<Tuple<FailedCase, SuccessCount>, SuccessCount> check() {
        return null; //Prop.this.check() && p.check();
      }
    };
  }
          
  public static <A> Prop forAll(Gen<A> intList, Function<A, Boolean> p) {
    return null;
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
