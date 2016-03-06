package com.fpinjava.io.exercise13_03;


import com.fpinjava.common.Result;
import com.fpinjava.common.Tuple;

import java.io.BufferedReader;

public class AbstractReader implements Input {

  protected final BufferedReader reader;

  protected AbstractReader(BufferedReader reader) {
    this.reader = reader;
  }

  @Override
  public Result<Tuple<String, Input>> readString() {
    try {
      String s = reader.readLine();
      return s.length() == 0
          ? Result.empty()
          : Result.success(new Tuple<>(s, this));
    } catch (Exception e) {
      return Result.failure(e);
    }
  }

  @Override
  public Result<Tuple<Integer, Input>> readInt() {
    try {
      String s = reader.readLine();
      return s.length() == 0
          ? Result.empty()
          : Result.success(new Tuple<>(Integer.parseInt(s), this));
    } catch (Exception e) {
      return Result.failure(e);
    }
  }
}
