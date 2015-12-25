package com.fpinjava.io.listing13_07;


import com.fpinjava.common.Result;
import com.fpinjava.io.listing13_02.Input;
import com.fpinjava.io.listing13_03.AbstractReader;

import java.io.*;

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
