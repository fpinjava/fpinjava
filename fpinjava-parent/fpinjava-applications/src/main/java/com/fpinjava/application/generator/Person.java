package com.fpinjava.application.generator;


import java.time.LocalDate;

public class Person {
  private final String firstName;
  private final String lastName;
  private final LocalDate birthDate;

  public Person(String firstName, String lastName, LocalDate birthDate) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthDate = birthDate;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  @Override
  public String toString() {
    return String.format("%s %s, %s", firstName, lastName, birthDate);
  }
}
