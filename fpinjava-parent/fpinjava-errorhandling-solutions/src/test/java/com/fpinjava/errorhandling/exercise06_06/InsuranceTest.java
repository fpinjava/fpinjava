package com.fpinjava.errorhandling.exercise06_06;

import static org.junit.Assert.*;

import org.junit.Test;


public class InsuranceTest {

  @Test
  public void testParseInsuranceRateQuote() {
    assertEquals("Right(11.5)", Insurance.parseInsuranceRateQuote("25", "2").toString());
  }

  @Test
  public void testParseInsuranceRateQuoteError1() {
    assertEquals("Left(java.lang.NumberFormatException: For input string: \"25,2\")", 
        Insurance.parseInsuranceRateQuote("25,2", "2").toString());
  }

  @Test
  public void testParseInsuranceRateQuoteError2() {
    assertEquals("Left(java.lang.NumberFormatException: For input string: \"3,4\")", 
        Insurance.parseInsuranceRateQuote("30", "3,4").toString());
  }

  @Test
  public void testParseInsuranceRateQuoteOrElse() {
    assertEquals("Right(11.5)", Insurance.parseInsuranceRateQuote("25", "2").orElse(() -> Either.right(100.0)).toString());
  }

  @Test
  public void testParseInsuranceRateQuoteError2orElse() {
    assertEquals("Right(100.0)", 
        Insurance.parseInsuranceRateQuote("30", "3,4").orElse(() -> Either.right(100.0)).toString());
  }

}
