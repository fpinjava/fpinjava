package com.fpinjava.application.generator;


import com.fpinjava.common.List;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EnglishNames {

  public static List<String> lastNames = read("EnglishNames.txt");

  public static List<String> firstNames = List.concat(read("BoysFirstNames.txt"), read("GirlsFirstNames.txt"));

  private static List<String> read(String fileName) {
    try {
      return List.fromCollection(Files.readAllLines(Paths.get(EnglishNames.class.getResource(fileName).toURI()), Charset.defaultCharset()));
    } catch (URISyntaxException | IOException e) {
      throw new IllegalStateException(e);
    }
  }
}
