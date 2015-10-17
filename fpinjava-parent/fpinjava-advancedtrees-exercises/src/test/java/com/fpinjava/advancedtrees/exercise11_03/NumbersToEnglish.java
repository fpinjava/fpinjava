package com.fpinjava.advancedtrees.exercise11_03;

import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Result;
import com.fpinjava.common.Tuple;


public final class NumbersToEnglish {

  private static final String[] numNames = {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
      "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};

  private static final String[] tensNames = {"", "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};

  private static final List<String> thousandNames = List.list("", "thousand", "million", "billion");

  private static final String SPACE = " ";

  private static final String DASH = "-";

  /**
   * Concat two string using an additional separator string. The separator is used only if both strings
   * are not empty. If one of the string is empty, the other is returned, without using the separator.
   * <p>
   * Example:
   * concatWithSeparator.apply(" and ").apply("Sam").apply("Dave") -> Sam and Dave
   * concatWithSeparator.apply(" and ").apply("").apply("Dave") -> Dave
   *
   */
  private static Function<String, Function<String, Function<String, String>>> concatWithSeparator = separator -> s1 -> s2 -> s1.length() == 0
      ? s2
      : s2.length() == 0
          ? s1
          : s1 + separator + s2;

  /**
   * Concat strings using a space as a separator if needed, i.e if both strings are not empty.
   */
  static Function<String, Function<String, String>> concatWithSpace = concatWithSeparator.apply(SPACE);

  /**
   * Concat strings using the word "and" as a separator if needed, i.e if both strings are not empty.
   */
  static Function<String, Function<String, String>> concatWithAnd = concatWithSeparator.apply(" and ");

  /**
   * Decompose a number into a list of factors. Those factors would allow recomputing the number
   * by multiplying them by 10 ^ (3 * index) and summing the results, where index is the position of
   * each factor in the list.
   *
   * Example: 1 234 567 890 = 890 * (10 ^ (3 * 0)) + 567 * (10 ^ (3 * 1)) + 234 * (10 ^ (3 * 2)) + 1 * (10 ^ (3 * 3))
   */
  static Function<Integer, List<Integer>> decompose = n -> List.unfold(n, n2 -> n2 > 0
      ? Result.success(new Tuple<>(n2 % 1000, n2 / 1000))
      : Result.<Tuple<Integer,Integer>>empty());


  /**
   * Convert an integer between 0 and 999 into a string representation using English number names.
   * This method does not check its argument, which is converted modulo 1000. This method is
   * intended to be used in larger conversion, in which the result is to be followed by the name
   * of a factor of the form (10 ^ (3 * n)) such a "thousand", "million" or "billion". As there is
   * no name for (10 ^ (3 * 0)), the conversion of 0 returns an empty string. All other results are
   * prefixed by a space. Conversion to GB English includes " and " after "hundred", like in
   * "hundred and twenty-one", whether US English conversion doe not.
   */
  static Function<Locale, Function<Integer, String>> convertUnder1000 = locale -> n -> {
    final int hundred = n % 1000 / 100;
    final int tens = n % 100;
    final int units = tens % 10;
    final String h = hundred == 0 ? "" : numNames[hundred] + " hundred";
    final String t = tens == 0
        ? units == 0
            ? ""
            : numNames[units]
        : tens < 20
            ? numNames[tens]
            : tensNames[(tens - units) / 10] + (units == 0 ? "" : DASH + numNames[units]);
    return locale == Locale.US
        ? concatWithSpace.apply(h).apply(t)
        : concatWithAnd.apply(h).apply(t);
  };

  /**
   * Convert a tuple (number, string) into a string, concatenating the string representation of the
   * number with the string parameter, adding a space separator if the string is not empty.
   */
  static Function<Locale, Function<Tuple<Integer, String>, String>> thousands2String = locale -> tuple -> tuple._1 == 0
      ? ""
      : concatWithSpace.apply(convertUnder1000.apply(locale).apply(tuple._1)).apply(tuple._2);

  /**
   * Convert an integer to its string representation using a Locale (American English or British English).
   */
  static Function<Locale, Function<Integer, String>> convert = locale -> n -> {
    final String result = n == 0
        ? "zero"
        : List.zipWith(decompose.apply(Math.abs(n)), thousandNames,
            i -> s -> new Tuple<>(i, s)).reverse().map(thousands2String.apply(locale)).foldLeft("", concatWithSpace);
    return n < 0
        ? "minus " + result
        : result;
  };

  /**
   * Convert an integer to its string representation British English.
   */
  public static Function<Integer, String> convertGB = convert.apply(Locale.GB);

  /**
   * Convert an integer to its string representation American English.
   */
  public static Function<Integer, String> convertUS = convert.apply(Locale.US);

  /**
   * The possible locale language variants.
   */
  public enum Locale {
    US, GB
  }
}
