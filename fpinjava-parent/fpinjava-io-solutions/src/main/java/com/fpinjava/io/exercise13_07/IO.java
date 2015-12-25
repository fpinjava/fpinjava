package com.fpinjava.io.exercise13_07;


public interface IO {

  void run();

  default IO add(IO io) {
    return () -> {
      IO.this.run();
      io.run();
    };
  }

  IO empty = () -> {};

}
