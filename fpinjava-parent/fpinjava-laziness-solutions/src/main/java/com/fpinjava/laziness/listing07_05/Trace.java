package com.fpinjava.laziness.listing07_05;

import static com.fpinjava.common.List.*;

import com.fpinjava.laziness.exercise07_08.Stream;
import static com.fpinjava.laziness.exercise07_08.Stream.*;

public class Trace {
  
  public static void main(String[] args) {
    
    System.out.println(cons(1,2,3,4).map(x -> x + 10).filter(x -> x % 2 == 0).toList());

    System.out.println(Stream.<Integer>cons(() -> 11, cons(2,3,4).map(x -> x + 10)).filter(x -> x % 2 == 0).toList()); //#A
     
    System.out.println(cons(2,3,4).map(x -> x + 10).filter(x -> x % 2 == 0).toList()); //#B
     
    System.out.println(Stream.<Integer>cons(() -> 12, cons(3,4).map(x -> x + 10)).filter(x -> x % 2 == 0).toList()); //#C
     
    System.out.println(concat(list(12), cons(3,4).map(x -> x + 10).filter(x -> x % 2 == 0).toList())); //#D
     
    System.out.println(concat(list(12), Stream.<Integer>cons(() -> 13, cons(4).map(x -> x + 10)).filter(x -> x % 2 == 0).toList()));

    System.out.println(concat(list(12), cons(4).map(x -> x + 10).filter(x -> x % 2 == 0).toList()));

    System.out.println(concat(list(12), Stream.<Integer>cons(() -> 14, Stream.<Integer>empty().map(x -> x + 10)).filter(x -> x % 2 == 0).toList()));

    System.out.println(concat(list(12, 14), Stream.<Integer>empty().map(x -> + 10).filter(x -> x % 2 == 0).toList())); //#E
     
    System.out.println(concat(list(12, 14),list())); //#F

  }
}
