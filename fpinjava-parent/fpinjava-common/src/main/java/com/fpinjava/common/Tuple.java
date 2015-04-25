package com.fpinjava.common;

import java.util.Objects;


public final class Tuple<T, U> {

  public final T _1;
  public final U _2;

  public Tuple(T t, U u) {
    this._1 = Objects.requireNonNull(t);
    this._2 = Objects.requireNonNull(u);
  }

  @Override
  public String toString() {
    return String.format("(%s,%s)", _1,  _2);
  }
  
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Tuple))
      return false;
    else {
      @SuppressWarnings("rawtypes")
      Tuple that = (Tuple) o;
      return _1.equals(that._1) && _2.equals(that._2);
    }
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + _1.hashCode();
    result = prime * result + _2.hashCode();
    return result;
  }

}