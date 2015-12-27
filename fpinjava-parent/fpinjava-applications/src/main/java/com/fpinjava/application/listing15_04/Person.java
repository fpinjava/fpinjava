package com.fpinjava.application.listing15_04;


import com.fpinjava.common.List;
import com.fpinjava.common.Result;

public class Person {

  private static final String FORMAT = "Person:\n\tID: %s\n" +
      "\tFirst name: %s\n" +
      "\tLast name: %s";
  public final int id;
  public final String firstName;
  public final String lastName;

  private Person(int id, String firstName, String lastName) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public static Person apply(int id, String firstName, String lastName) {
    return new Person(id, firstName, lastName);
  }

  @Override
  public String toString() {
    return String.format(FORMAT, id, firstName, lastName);
  }

  public static Result<Person> getAsPerson(String propertyName, PropertyReader propertyReader) {
    Result<String> rString = propertyReader.getAsPropertyString(propertyName);
    rString.forEach(System.out::println);
    Result<PropertyReader> rPropReader = rString.map(PropertyReader::stringPropertyReader);
    return rPropReader.flatMap(Person::readPerson);
  }

  public static Result<List<Person>> getAsPersonList(String propertyName, PropertyReader propertyReader) {
    Result<List<String>> rList = propertyReader.getAsStringList(propertyName);
    return rList.flatMap(list -> List.sequence(list.map(s -> readPerson(PropertyReader.stringPropertyReader(PropertyReader.toPropertyString(s))))));
  }

  private static Result<Person> readPerson(PropertyReader propReader) {
    return propReader.getAsInteger("id")
        .flatMap(id -> propReader.getAsString("firstName")
            .flatMap(firstName -> propReader.getAsString("lastName")
                .map(lastName -> Person.apply(id, firstName, lastName))));
  }

}
