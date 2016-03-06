package com.fpinjava.io.exercise13_04;


import com.fpinjava.common.Result;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class FileReader extends AbstractReader {

  private FileReader(BufferedReader reader) {
    super(reader);
  }

  public static Result<Input> fileReader(String path) {
    try {
      return Result.success(new FileReader(new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))))));
    } catch (Exception e) {
      return Result.failure(e);
    }
  }
}
