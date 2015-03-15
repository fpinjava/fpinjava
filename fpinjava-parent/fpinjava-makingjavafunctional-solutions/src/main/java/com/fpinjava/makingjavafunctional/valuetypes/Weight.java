package com.fpinjava.makingjavafunctional.valuetypes;

import com.fpinjava.common.Effect;
import com.fpinjava.common.Function;

public class Weight {

  public static final Weight ZERO = new Weight(0.0);

  public static Function<Weight, Function<OrderLine, Weight>> sum = x -> y -> x.add(y.getWeight());

  public final double value;

  private Weight(double value) {
    this.value = value;
  }

  public static Weight weight(double value) {
    if (value <= 0) {
      throw new IllegalArgumentException("Weight must be greater than 0");
    } else {
      return new Weight(value);
    }
  }

  public Weight add(Weight that) {
    return weight(this.value + that.value);
  }

  public Weight mult(int count) {
    return weight(this.value * count);
  }

  public void bind(Effect<Double> effect) {
    effect.apply(this.value);
  }

  public String toString() {
    return Double.toString(this.value);
  }
}
