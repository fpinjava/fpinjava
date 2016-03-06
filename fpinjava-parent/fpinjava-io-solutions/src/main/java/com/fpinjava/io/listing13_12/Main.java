package com.fpinjava.io.listing13_12;


import com.fpinjava.common.Nothing;
import com.fpinjava.io.exercise13_07.Console;
import com.fpinjava.io.exercise13_07.IO;

public class Main {

  public static void main(String... args) {
    IO<Nothing> script = sayHello();
    script.run();
  }

  private static IO<Nothing> sayHello() {
    return Console.printLine("Enter your name: ")
        .flatMap(Console::readLine)
        .map(Main::buildMessage)
        .flatMap(Console::printLine);
  }

  private static String buildMessage(String name) {
    return String.format("Hello, %s!", name);
  }
}

