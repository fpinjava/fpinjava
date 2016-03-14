package com.fpinjava.io.listing13_04;


import com.fpinjava.common.Result;
import com.fpinjava.common.Tuple;
import com.fpinjava.io.listing13_02.Input;
import com.fpinjava.io.listing13_03.AbstractReader;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConsoleReader extends AbstractReader {

  protected ConsoleReader(BufferedReader reader) {
    super(reader);
  }

  @Override
  public Result<Tuple<String, Input>> readString(String message) {
    System.out.print(message + " ");
    return readString();
  }

  @Override
  public Result<Tuple<Integer, Input>> readInt(String message) {
    System.out.print(message + " ");
    return readInt();
  }

  public static ConsoleReader consoleReader() {
    return new ConsoleReader(new BufferedReader(new InputStreamReader(System.in)));
  }
}
