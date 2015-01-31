package com.fpinjava.makingjavafunctional.valuetypes;

public class OrderLine {

  private final Product product;
  private final int count;

  public OrderLine(Product product, int count) {
    this.product = product;
    this.count = count;
  }

  public Weight getWeight() {
    return this.product.weight.mult(this.count);
  }

  public Price getAmount() {
    return this.product.price.mult(this.count);
  }
}
