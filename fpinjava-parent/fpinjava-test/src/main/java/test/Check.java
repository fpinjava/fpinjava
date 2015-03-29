package test;

import java.math.BigDecimal;


public class Check {

  public static void main(String[] args) {
    BigDecimal result = BigDecimal.ZERO;
    for (long x = 0; x < 400000; x++) {
      result = result.add(BigDecimal.valueOf(x * x));
    }
    System.out.println(result);
  }

}
