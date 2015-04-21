package com.fpinjava.propertybasedtesting.exercise03;

public interface Prop {

  public boolean check();

  default Prop and_(Prop p) {
    return new Prop() {

      @Override
      public boolean check() {
        return Prop.this.check() && p.check();
      }
    };
  }

  /*
   * Unlike in an anonymous class, in a lambda, this refers to teh encolsing
   * class, so we do not need to write Prop.this.check().
   */
  default Prop and(Prop p) {
    return () -> this.check() && p.check();
  }

}
