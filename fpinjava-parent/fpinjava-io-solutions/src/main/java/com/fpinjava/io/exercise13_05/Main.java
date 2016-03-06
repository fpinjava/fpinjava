package com.fpinjava.io.exercise13_05;


import com.fpinjava.common.List;

public class Main {

  public static void main(String... args) {

    String name = getName();

    // These three lines do nothing. each line returns a program with a
    IO instruction1 = println("Hello, ");
    IO instruction2 = println(name);
    IO instruction3 = println("!\n");

    // Write a script
    IO script = instruction1.add(instruction2).add(instruction3);
    // execute it
    script.run();

    // Or simpler:
    println("Hello, ").add(println(name)).add(println("!\n")).run();

    // We can make a script from a list of instructions:

    List<IO> script2 = List.list(
        println("Hello, "),  // Doesn't this look like an imperative program?
        println(name),
        println("!\n")
    );
    // We can sort of "compile" it, then execute it
    script2.foldRight(IO.empty, io -> io::add).run();
    script2.foldLeft(IO.empty, acc -> acc::add).run();
  }

  private static IO println(String name) {
    return () -> System.out.print(name);
  }

  private static String getName() {
    return "Mickey";
  }
}

