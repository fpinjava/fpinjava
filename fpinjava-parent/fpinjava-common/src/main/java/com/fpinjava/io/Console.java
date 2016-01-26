package com.fpinjava.io;


import com.fpinjava.common.Nothing;
import com.fpinjava.common.Result;

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

  /**
   * Get a string from the console after displaying a message.
   *
   * @param prompt The message prompting the user to enter something
   * @return The string entered by the user
   */
  public static IO<String> readLine(String prompt) {
    return printLine(prompt).flatMap(x -> readLine(Nothing.instance));
  }

  public static IO<Nothing> printLine(Result<Object> r) {
    return () -> {
      r.forEachOrFail(System.out::println)
       .forEach(System.out::println);
      return Nothing.instance;
    };
  }

  public static IO<Nothing> printLine(Object o) {
    return () -> {
      System.out.println(o);
      return Nothing.instance;
    };
  }

  public static IO<Nothing> print(Object s) {
    return () -> {
      System.out.print(s);
      return Nothing.instance;
    };
  }
}
