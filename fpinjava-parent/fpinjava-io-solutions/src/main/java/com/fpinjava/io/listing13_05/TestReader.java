package com.fpinjava.io.listing13_05;


import com.fpinjava.common.Result;
import com.fpinjava.io.listing13_02.Input;
import com.fpinjava.io.listing13_04.ConsoleReader;

public class TestReader {

  public static void main(String... args) {

    Input input = ConsoleReader.consoleReader();

    Result<String> rString = input.readString("Enter your name: ").map(t -> t._1);

    Result<String> result = rString.map(s -> String.format("Hello, %s!", s));

    result.forEachOrFail(System.out::println).forEach(System.out::println);
  }
}
