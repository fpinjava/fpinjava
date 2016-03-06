package com.fpinjava.io.exercise13_07;


import com.fpinjava.common.Nothing;

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

