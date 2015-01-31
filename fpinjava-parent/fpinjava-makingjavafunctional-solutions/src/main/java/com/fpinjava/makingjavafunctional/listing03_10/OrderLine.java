package com.fpinjava.makingjavafunctional.listing03_10;

public class OrderLine {

  private Product product;
  private int count;

  public OrderLine(Product product, int count) {
    super();
    this.product = product;
    this.count = count;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public double getWeight() {
    return this.product.getWeight() * this.count;
  }

  public double getAmount() {
    return this.product.getPrice() * this.count;
  }
}