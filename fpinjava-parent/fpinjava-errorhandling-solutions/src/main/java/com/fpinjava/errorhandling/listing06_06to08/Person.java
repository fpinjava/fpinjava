package com.fpinjava.errorhandling.listing06_06to08;

import static com.fpinjava.errorhandling.listing06_06to08.Age.age;
import static com.fpinjava.errorhandling.listing06_06to08.Name.name;

import com.fpinjava.errorhandling.exercise06_07.Either;


public class Person {

  public final Name name;
  public final Age age;

  private Person(Name name, Age age) {
    this.name = name;
    this.age = age;
  }
  
  @Override
  public String toString() {
    return String.format("[Person: %s, %s]", name, age);
  }

  public static Either<String, Person> person(String name, int age) {
    return name(name).map2(age(age), x -> y -> new Person(x, y));
  }
}