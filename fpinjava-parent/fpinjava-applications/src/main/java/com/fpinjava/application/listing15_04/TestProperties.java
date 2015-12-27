package com.fpinjava.application.listing15_04;


import com.fpinjava.common.List;
import com.fpinjava.common.Result;

public class TestProperties {

  public static void main(String... args) {
    PropertyReader propertyReader = PropertyReader.filePropertyReader("config.properties");

    propertyReader.getAsString("host")
                  .forEachOrFail(System.out::println)
                  .forEach(System.out::println);

    propertyReader.getAsString("name")
                  .forEachOrFail(System.out::println)
                  .forEach(System.out::println);

    propertyReader.getAsString("year")
                  .forEachOrFail(System.out::println)
                  .forEach(System.out::println);

    Result<Person> person1 =
        propertyReader.getAsInteger("id")
              .flatMap(id -> propertyReader.getAsString("firstName")
                   .flatMap(firstName -> propertyReader.getAsString("lastName")
                        .map(lastName -> Person.apply(id, firstName, lastName))));

    person1.forEachOrFail(System.out::println).forEach(System.out::println);

    Result<List<Integer>> list = propertyReader.getAsIntegerList("list");
    list.forEachOrFail(System.out::println).forEach(System.out::println);

    Result<Type> type = propertyReader.getAsEnum("type", Type.class);
    type.forEachOrFail(System.out::println).forEach(System.out::println);

    Result<Person> person2 = Person.getAsPerson("person", propertyReader);
    person2.forEachOrFail(System.out::println).forEach(System.out::println);

    Result<List<Person>> employees = Person.getAsPersonList("employees", propertyReader);
    employees.forEachOrFail(lst -> lst.forEach(System.out::println)).forEach(System.out::println);
  }
}
