package com.fpinjava.application.listing15_04;


import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

public class TestPropertyString {

  public static void main(String... args) throws IOException {
    Properties properties = new Properties();
    properties.load(new StringReader("id:3\nfirstName:Jane\nlastName:Doe"));
    String firstName = properties.getProperty("firstName");
    System.out.println(firstName);
  }
}
