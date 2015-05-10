package com.fpinjava.makingjavafunctional.listing03_03;

public interface Result {

  class Success implements Result {}

  class Failure implements Result {

    private final String errorMessage;

    public Failure(String s) {
      this.errorMessage = s;
    }

    public String getMessage() {
      return errorMessage;
    }
  }
}
