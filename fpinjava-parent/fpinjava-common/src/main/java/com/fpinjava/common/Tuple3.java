package com.fpinjava.common;

import java.util.Objects;

public class Tuple3<T, U, V> {

	public final T _1;
  public final U _2;
  public final V _3;

	public Tuple3(T t, U u, V v) {
		this._1 = Objects.requireNonNull(t);
    this._2 = Objects.requireNonNull(u);
    this._3 = Objects.requireNonNull(v);
	}
	
  @Override
  public String toString() {
    return String.format("(%s,%s,%s)", _1,  _2, _3);
  }
}