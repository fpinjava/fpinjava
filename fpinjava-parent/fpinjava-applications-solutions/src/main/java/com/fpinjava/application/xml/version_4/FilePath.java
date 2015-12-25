package com.fpinjava.application.xml.version_4;


import com.fpinjava.common.Result;

public class FilePath {

  public final Result<String> value;

  private FilePath(Result<String> value) {
    this.value = value;
  }

  public static FilePath apply(String value) {
    return new FilePath(Result.of(FilePath::isValidPath, value, "Invalid file path: " + value));
  }

  private static boolean isValidPath(String path) {
    return true;
  }
}
