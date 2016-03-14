package com.fpinjava.io.listing13_08;


import com.fpinjava.common.Result;
import com.fpinjava.common.Stream;
import com.fpinjava.common.Tuple;
import com.fpinjava.io.listing13_02.Input;
import com.fpinjava.io.listing13_06.Person;
import com.fpinjava.io.listing13_07.FileReader;

public class ReadFile {

  private static String path = "path to data file";

  public static void main(String... args) {
    Result<Input> rInput = FileReader.fileReader(path);

    Result<Stream<Person>> rStream = rInput.map(input -> Stream.unfold(input, ReadFile::person));
    rStream.forEachOrFail(stream -> stream.toList().forEach(System.out::println)).forEach(System.out::println);
  }

  public static Result<Tuple<Person, Input>> person(Input input) {
    return input.readInt("Enter ID:")
        .flatMap(id -> id._2.readString("Enter first name:")
            .flatMap(firstName -> firstName._2.readString("Enter last name:")
                .map(lastName -> new Tuple<>(Person.apply(id._1,
                    firstName._1, lastName._1), lastName._2))));
  }
}
