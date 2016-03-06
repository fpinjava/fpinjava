package com.fpinjava.io.exercise13_07;


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

  public static IO<Nothing> printLine(Object o) {
    return () -> {
      System.out.println(o.toString());
      return Nothing.instance;
    };
  }
}
