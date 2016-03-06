package com.fpinjava.io.exercise13_08;


import com.fpinjava.common.Nothing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console {

  private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  public static IO<String> readLine(Nothing nothing) {
    return () -> {
      try {
        return br.readLine();
      } catch (IOException e) {
        throw new IllegalStateException((e));
      }
    };
  }

  public static IO<String> readLine() {
    return readLine(Nothing.instance);
  }

  public static IO<Nothing> printLine(Object s) {
    return () -> {
      System.out.println(s);
      return Nothing.instance;
    };
  }
}
