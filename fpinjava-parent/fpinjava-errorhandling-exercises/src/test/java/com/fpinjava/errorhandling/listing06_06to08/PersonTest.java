package com.fpinjava.errorhandling.listing06_06to08;

import static org.junit.Assert.*;

import org.junit.Test;

public class PersonTest {

  @Test
  public void testPerson() {
    assertEquals("Right([Person: [Name: John Doe], [Age: 27]])", Person.person("John Doe", 27).toString());
  }

  @Test
  public void testPersonInvalidName() {
    assertEquals("Left(Name is empty)", Person.person("", 27).toString());
  }

  @Test
  public void testPersonInvalidAge() {
    assertEquals("Left(Age is out of range)", Person.person("John Doe", 127).toString());
  }

  @Test
  public void testPersonInvalidNameAndAge() {
    assertEquals("Left(Name is empty)", Person.person("", -27).toString());
  }

}
