package com.fpinjava.io.exercise13_03;


import com.fpinjava.common.Result;
import com.fpinjava.common.Stream;
import com.fpinjava.common.Tuple;


public class ReadConsole {

  public static void main(String... args) {
    Input input = ConsoleReader.consoleReader();

    Stream<Person> stream = Stream.unfold(input, ReadConsole::person);
    stream.toList().forEach(System.out::println);
  }

  public static Result<Tuple<Person, Input>> person(Input input) {
    return input.readInt("Enter ID:")
       .flatMap(id -> id._2.readString("Enter first name:")
           .flatMap(firstName -> firstName._2.readString("Enter last name:")
               .map(lastName -> new Tuple<>(Person.apply(id._1, firstName._1, lastName._1), lastName._2))));
  }
}
