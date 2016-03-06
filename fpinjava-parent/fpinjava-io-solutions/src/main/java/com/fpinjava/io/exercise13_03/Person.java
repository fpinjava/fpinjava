package com.fpinjava.io.exercise13_03;


public class Person {

  private static final String FORMAT = "ID: %s, First name: %s, Last name: %s";
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
}
