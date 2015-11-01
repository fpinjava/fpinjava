package com.fpinjava.state.exercise12_10_;


public class Point {

  public final int x;
  public final int y;
  public final int z;

  public Point(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @Override
  public String toString() {
    return String.format("Point(%s, %s, %s)", x, y, z);
  }
}
