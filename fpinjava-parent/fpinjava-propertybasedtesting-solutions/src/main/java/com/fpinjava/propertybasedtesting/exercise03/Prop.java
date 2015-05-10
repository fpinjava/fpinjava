package com.fpinjava.propertybasedtesting.exercise03;

public interface Prop {

  boolean check();

  default Prop and(Prop p) {
    return () -> this.check() && p.check();
  }

}
