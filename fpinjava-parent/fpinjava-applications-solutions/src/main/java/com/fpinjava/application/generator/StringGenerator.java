package com.fpinjava.application.generator;


import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Tuple;

public class StringGenerator implements Generator<String> {

  protected final ListGenerator<Character> generator;

  protected final Function<String, Boolean> filter;

  /**
   * 2 means 2% empty strings
   */
  public StringGenerator(long seed) {
    generator = Generator.listGenerator_(seed, Generator.charGenerator_(), 2);
    this.filter = x -> true;
  }

  public StringGenerator() {
    generator = Generator.listGenerator_(Generator.charGenerator_(), 2);
    this.filter = x -> true;
  }

  public StringGenerator(long seed, Function<Character, Boolean> charFilter) {
    generator = Generator.listGenerator_(seed, Generator.charGenerator_(charFilter), 2);
    this.filter = x -> true;
  }

  public StringGenerator(Function<Character, Boolean> charFilter) {
    generator = Generator.listGenerator_(Generator.charGenerator(charFilter), 2);
    this.filter = x -> true;
  }

  public StringGenerator(long seed, Function<Character, Boolean> charFilter, Function<String, Boolean> stringFilter) {
    generator = Generator.listGenerator_(seed, Generator.charGenerator_(charFilter), 2);
    this.filter = stringFilter;
  }

  public StringGenerator(Function<Character, Boolean> charFilter, Function<String, Boolean> stringFilter) {
    generator = Generator.listGenerator_(Generator.charGenerator_(charFilter), 2);
    this.filter = stringFilter;
  }

  // Broken after removing getOrThrow from Result
  @Override
  public String next() {
    List<Character> list = generator.next();
    char[] chars = new char[list.length()];
    Function<char[], Function<Tuple<Character, Integer>, char[]>> f = b -> tci -> insert(b, tci);
    String string = "";//String.valueOf(list.zipWithPosition().foldLeft(chars, f));
    return filter.apply(string)
        ? string
        : next(); // Careful: possible stack overflow if infinite recursion. Check the depth and return Result.success or Result.failure if more than 1000 consecutive failure to satisfy predicate.
  }

  private static char[] insert(char[] tc, Tuple<Character, Integer> tci) {
    tc[tci._2] = tci._1;
    return tc;
  }
}
