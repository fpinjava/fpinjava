package com.fpinjava.application.properties;


import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

public class Test {

  public static void main(String... args) throws IOException {
    Properties properties = new Properties();
    properties.load(new StringReader("id:3\nfirstName:Jeanne\nlastName:Doe"));
    String firstName = properties.getProperty("firstName");
    System.out.println(firstName);
  }
}
