package com.fpinjava.functionalstate.listing08_01;

import java.util.Random;

public class SimpleRNG {
  
  public static void main(String[] args) {
    Random rng = new Random();
    System.out.println(rng.nextDouble());
    System.out.println(rng.nextDouble());
    System.out.println(rng.nextInt());
    System.out.println(rng.nextInt(10));
  }
}
