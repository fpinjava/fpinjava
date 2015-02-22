package com.fpinjava.laziness;

import com.fpinjava.common.Supplier;


public class Test1 {

  public static void main(String[] args) {
    //List<Integer> list = List.list(1,2,3,4).map(x -> x + 10).filter(x -> x % 2 == 0).map(x -> x * 3);
    //System.out.println(list);
    
    //boolean value = false && getValue();
    
    int a = 24;
    int b = 22;
    String message = if2(a < b, () -> getLower(a, b), () -> getHigher(a, b));
    System.out.println(message);

  }
  
  private static String getLower(int a, int b) {
    System.out.println("in getLower");
    return a + " < " + b;
  }
  
  private static String getHigher(int a, int b) {
    System.out.println("in getHigher");
    return a + " >= " + b;
  }
  
  static <A> A if2(boolean condition, Supplier<A> onTrue, Supplier<A> onFalse) {
      return condition 
          ? onTrue.get()
          : onFalse.get();
  }
  
  
  static boolean getValue() {
    throw new RuntimeException();
  }

}
