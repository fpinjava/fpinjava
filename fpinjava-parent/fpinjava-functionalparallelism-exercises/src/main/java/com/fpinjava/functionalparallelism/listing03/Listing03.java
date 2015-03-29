package com.fpinjava.functionalparallelism.listing03;

import static com.fpinjava.common.List.list;
import static com.fpinjava.functionalparallelism.listing02.Listing02.sum;
import static com.fpinjava.functionalparallelism.listing03.Par.map2;
import static com.fpinjava.functionalparallelism.listing03.Par.unit;


public class Listing03 {

  public static void sumExample() {
    
    sum(list(1,2,3,4));
    
    map2(
        unit(() -> sum(list(1,2))), 
        unit(() -> sum(list(3,4))), x -> y -> x + y);
   
    map2(
        Par.<Integer, Integer, Integer>map2(
            unit(() -> sum(list(1))), 
            unit(() -> sum(list(2))), x -> y -> x + y), 
        unit(() -> sum(list(4))), x -> y -> x + y);
   
    map2(
        Par.<Integer, Integer, Integer>map2(
            unit(() -> sum(list(1))), 
            unit(() -> sum(list(2))), x -> y -> x + y), 
        Par.<Integer, Integer, Integer>map2(
            unit(() -> sum(list(3))), 
            unit(() -> sum(list(4))), x -> y -> x + y), x -> y -> x + y);
  }

}



