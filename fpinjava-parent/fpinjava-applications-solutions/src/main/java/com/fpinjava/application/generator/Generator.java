package com.fpinjava.application.generator;


import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Tuple;

import java.time.*;
import java.util.Date;
import java.util.Random;


public interface Generator<T> {

  T next();

  default <U> Generator<U> map(Function<T, U> f) {
    return () -> f.apply(Generator.this.next());
  }

  default <U> Generator<U> flatMap(Function<T, Generator<U>> f) {
    return () -> f.apply(Generator.this.next()).next();
  }

  public static <T> Generator<T> unit(T t) {
    return () -> t;
  }

  static Generator<Integer> intGenerator(long seed) {
    return new Generator<Integer>() {

      private final Random random = new Random(seed);

      @Override
      public Integer next() {
        return random.nextInt();
      }
    };
  }

  static Generator<Integer> intGenerator() {
    return new Generator<Integer>() {

      private final Random random = new Random();

      @Override
      public Integer next() {
        return random.nextInt();
      }
    };
  }

  static Generator<Integer> intGenerator(long seed, Function<Integer, Boolean> filter) {
    Generator<Integer> generator = intGenerator(seed);
    return new Generator<Integer>() {
      @Override
      public Integer next() {
        int x = generator.next();
        return filter.apply(x)
            ? x
            : this.next(); // this is a reference to the enclosing anonymous class. It would not work with a lambda.
      }
    };
  }

  static Generator<Boolean> booleanGenerator(long seed, int percentTrue) {
    return getBooleanGenerator(percentTrue, intGenerator(seed));
  }

  static Generator<Boolean> booleanGenerator(long seed) {
    return booleanGenerator(seed, 50);
  }

  static Generator<Boolean> booleanGenerator(int percentTrue) {
    return getBooleanGenerator(percentTrue, intGenerator());
  }

  static Generator<Boolean> getBooleanGenerator(int percentTrue, Generator<Integer> intGenerator) {
    int validatedPercentTrue = Math.max(0, Math.min(100, percentTrue));
    return intGenerator.map(x ->  Math.abs(x) % 100 < validatedPercentTrue);
  }

  static Generator<Boolean> booleanGenerator() {
    return booleanGenerator(50);
  }

  static Generator<Tuple<Integer, Integer>> tupleIntGenerator(long seed) {
    return getTupleGenerator(intGenerator(seed));
  }

  static Generator<Tuple<Integer, Integer>> tupleIntGenerator() {
    return getTupleGenerator(intGenerator());
  }

  static Generator<Tuple<Integer, Integer>> getTupleGenerator(Generator<Integer> generator) {
    return generator.flatMap(left -> generator.map(right -> new Tuple<>(left, right)));
  }

  static <T> ListGenerator<T> listGenerator_(long seed, Generator<T> generator) {
    return listGenerator_(seed, generator, 10);
  }

  static <T> ListGenerator<T> listGenerator_(Generator<T> generator) {
    return listGenerator_(generator, 10);
  }

  static <T> ListGenerator<T> listGenerator_(Generator<T> generator, int percentEmpty) {
    return new ListGenerator<T>(generator, percentEmpty);
  }

  static <T> ListGenerator<T> listGenerator_(long seed, Generator<T> generator, int percentEmpty) {
    return new ListGenerator<T>(seed, generator, percentEmpty);
  }

  static <T> Generator<List<T>> listGenerator(long seed, Generator<T> elementGenerator, int percentEmpty) {
    return getListGenerator(elementGenerator, booleanGenerator(seed, percentEmpty), x -> true);
  }

  static <T> Generator<List<T>> listGenerator(long seed, Generator<T> elementGenerator, int percentEmpty, Function<List<T>, Boolean> filter) {
    return getListGenerator(elementGenerator, booleanGenerator(seed, percentEmpty), filter);
  }

  static <T> Generator<List<T>> listGenerator(Generator<T> elementGenerator, int percentEmpty) {
    return getListGenerator(elementGenerator, booleanGenerator(percentEmpty), x -> true);
  }

  static <T> Generator<List<T>> listGenerator(Generator<T> elementGenerator, int percentEmpty, Function<List<T>, Boolean> filter) {
    return getListGenerator(elementGenerator, booleanGenerator(percentEmpty), filter);
  }

  static <T> Generator<List<T>> getListGenerator(final Generator<T> elementGenerator, final Generator<Boolean> booleanGenerator, Function<List<T>, Boolean> filter) {
    /*
     * A class is necessary to allow for a recursive lambda.
     */
    class ListGenerator {
      Generator<List<T>> generator = booleanGenerator.flatMap(empty -> empty
          ? List::list
          : elementGenerator.flatMap(h -> this.generator.map(t -> List.cons(h, t))));
    }
    Generator<List<T>> listGenerator = new ListGenerator().generator;
    return new Generator<List<T>>() {
      @Override
      public List<T> next() {
        List<T> list = listGenerator.next();
        return filter.apply(list) ? list : this.next(); // We can't use a lambda because of this recursion
      }
    };
  }

  static ByteGenerator byteGenerator(long seed) {
    return new ByteGenerator(seed);
  }

  static ByteGenerator byteGenerator() {
    return new ByteGenerator();
  }

  static ByteGenerator byteGenerator(long seed, Function<Byte, Boolean> filter) {
    return new ByteGenerator(seed, filter);
  }

  static ByteGenerator byteGenerator(Function<Byte, Boolean> filter) {
    return new ByteGenerator(filter);
  }

  static CharGenerator charGenerator_(long seed) {
    return new CharGenerator(seed);
  }

  static CharGenerator charGenerator_() {
    return new CharGenerator();
  }

  static CharGenerator charGenerator_(long seed, Function<Character, Boolean> filter) {
    return new CharGenerator(seed, filter);
  }

  static CharGenerator charGenerator_(Function<Character, Boolean> filter) {
    return new CharGenerator(filter);
  }

  static Generator<Character> charGenerator(long seed) {
    return getCharacterGenerator(x -> true, intGenerator(seed));
  }

  static Generator<Character> charGenerator() {
    return getCharacterGenerator(x -> true, intGenerator());
  }

  static Generator<Character> charGenerator(long seed, Function<Character, Boolean> filter) {
    return getCharacterGenerator(filter, intGenerator(seed));
  }

  static Generator<Character> charGenerator(Function<Character, Boolean> filter) {
    return getCharacterGenerator(filter, intGenerator());
  }

  static Generator<Character> getCharacterGenerator(final Function<Character, Boolean> filter, final Generator<Integer> intGenerator) {
    class CharGenerator {
      Generator<Character> generator = intGenerator.map(x -> {
        char b = (char) (Math.abs(x) % 256);
        return filter.apply(b)
            ? b
            : this.generator.next(); // caution: if filter is always false, will stack overflow!
      });
    }
    return new CharGenerator().generator;
  }

  static Generator<String> stringGenerator_() {
    return new StringGenerator();
  }

  static Generator<String> stringGenerator_(long seed, Function<Character, Boolean> charFilter) {
    return new StringGenerator(seed, charFilter);
  }

  static Generator<String> stringGenerator_(Function<Character, Boolean> charFilter) {
    return new StringGenerator(charFilter);
  }

  static Generator<String> stringGenerator_(long seed, Function<Character, Boolean> charFilter, Function<String, Boolean> stringFilter) {
    return new StringGenerator(seed, charFilter, stringFilter);
  }

  static Generator<String> stringGenerator_(Function<Character, Boolean> charFilter, Function<String, Boolean> stringFilter) {
    return new StringGenerator(charFilter, stringFilter);
  }

  static Generator<String> stringGenerator(long seed) {
    return getStringGenerator(x -> true, Generator.listGenerator(seed, charGenerator(seed), 2));
  }

  static Generator<String> stringGenerator() {
    return getStringGenerator(x -> true, Generator.listGenerator(charGenerator(), 2));
  }

  static Generator<String> stringGenerator(long seed, Function<Character, Boolean> charFilter) {
    return getStringGenerator(x -> true, Generator.listGenerator(seed, charGenerator(seed, charFilter), 2));
  }

  static Generator<String> stringGenerator(Function<Character, Boolean> charFilter) {
    return getStringGenerator(x -> true, Generator.listGenerator(charGenerator(charFilter), 2));
  }

  static Generator<String> stringGenerator(long seed, Function<Character, Boolean> charFilter, Function<String, Boolean> stringFilter) {
    return getStringGenerator(stringFilter, Generator.listGenerator(seed, charGenerator(seed, charFilter), 2));
  }

  static Generator<String> stringGenerator(Function<Character, Boolean> charFilter, Function<String, Boolean> stringFilter) {
    return getStringGenerator(stringFilter, Generator.listGenerator(charGenerator(charFilter), 2));
  }

  // Broken after removing getOrThrow from Result
  /**
   * Careful: possible stack overflow if infinite recursion. Check the depth and return Result.success
   * or Result.failure if more than 1000 consecutive failure to satisfy predicate.
   */
  static Generator<String> getStringGenerator(final Function<String, Boolean> stringFilter, final Generator<List<Character>> listGenerator) {
    class StringGenerator {
      Generator<String> generator = listGenerator.map(list -> {
        char[] chars = new char[list.length()];
        Function<char[], Function<Tuple<Character, Integer>, char[]>> f = b -> tci -> insert(b, tci);
        String string = "";//String.valueOf(list.zipWithPosition().foldLeft(chars, f));
        return stringFilter.apply(string)
            ? string
            : this.generator.next();
      });

      char[] insert(char[] tc, Tuple<Character, Integer> tci) {
        tc[tci._2] = tci._1;
        return tc;
      }
    }
    return new StringGenerator().generator;
  }

  static Generator<String> nameGenerator_(long seed) {
    return new NameGenerator(seed);
  }

  static Generator<String> nameGenerator_() {
    return new NameGenerator();
  }

  static Generator<String> wordGenerator(long seed) {
    Generator<Integer> intGenerator = intGenerator(seed);
    Function<List<Integer>, Boolean> lengthFilter = x -> true;
    Generator<List<Integer>> listGenerator = listGenerator(seed, intGenerator, 1, lengthFilter);
    Function<String, Boolean> filter = x -> true;

    return getStringGenerator(filter, intGenerator, listGenerator);
  }

  static Generator<String> wordGenerator() {
    Generator<Integer> intGenerator = intGenerator();
    Function<List<Integer>, Boolean> lengthFilter = x -> true;
    Generator<List<Integer>> listGenerator = listGenerator(intGenerator, 1, lengthFilter);
    Function<String, Boolean> filter = x -> true;

    return getStringGenerator(filter, intGenerator, listGenerator);
  }

  static Generator<String> wordGenerator(long seed, int minLength, int maxLength) {
    Generator<Integer> intGenerator = intGenerator(seed);
    Function<List<Integer>, Boolean> lengthFilter = list -> list.length() >= minLength && list.length() < maxLength;
    Generator<List<Integer>> listGenerator = listGenerator(seed, intGenerator, 1, lengthFilter);
    Function<String, Boolean> filter = x -> true;

    return getStringGenerator(filter, intGenerator, listGenerator);
  }

  static Generator<String> wordGenerator(int minLength, int maxLength) {
    Generator<Integer> intGenerator = intGenerator();
    Function<List<Integer>, Boolean> lengthFilter = list -> list.length() >= minLength && list.length() < maxLength;
    Generator<List<Integer>> listGenerator = listGenerator(intGenerator, 1, lengthFilter);
    Function<String, Boolean> filter = x -> true;

    return getStringGenerator(filter, intGenerator, listGenerator);
  }

  static Generator<String> wordGenerator(long seed, int minLength, int maxLength, Function<String, Boolean> filter) {
    Generator<Integer> intGenerator = intGenerator(seed);
    Function<List<Integer>, Boolean> lengthFilter = list -> list.length() >= minLength && list.length() < maxLength;
    Generator<List<Integer>> listGenerator = listGenerator(seed, intGenerator, 1, lengthFilter);

    return getStringGenerator(filter, intGenerator, listGenerator);
  }

  static Generator<String> wordGenerator(int minLength, int maxLength, Function<String, Boolean> filter) {
    Generator<Integer> intGenerator = intGenerator();
    Function<List<Integer>, Boolean> lengthFilter = list -> list.length() >= minLength && list.length() < maxLength;
    Generator<List<Integer>> listGenerator = listGenerator(intGenerator, 1, lengthFilter);

    return getStringGenerator(filter, intGenerator, listGenerator);
  }

  static Generator<String> getStringGenerator(final Function<String, Boolean> filter, final Generator<Integer> intGenerator, final Generator<List<Integer>> listGenerator) {
    class WordGenerator {
      List<String> consonantList = List.list("b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "p", "r", "s", "t", "v", "w", "x", "z", "bl", "br", "ch", "ck", "cl", "cr", "dr", "fl", "fr", "gh", "gl", "gr", "ng", "ph", "pl", "pr", "qu", "sc", "sh", "sk", "sl", "sm", "sn", "sp", "st", "sw", "th", "tr", "tw", "wh", "wr", "nth", "sch", "scr", "shr", "spl", "spr", "squ", "str", "thr");
      List<String> vowelList = List.list("a", "e", "i", "o", "u", "ai", "au", "aw", "ay", "ea", "ee", "ei", "eu", "ew", "ey", "ie", "oi", "oo", "ou", "ow", "oy");

      Generator<String> consonantGenerator = Generator.chooserGenerator_(consonantList, "Error: empty list");
      Generator<String> vowelGenerator = Generator.chooserGenerator_(vowelList, "Error: empty list");
      Generator<String> generator = intGenerator.map(i -> {
        String string = listGenerator.next().map(x -> consonantGenerator.next() + vowelGenerator.next()).foldLeft("", x -> y -> x + y);
        return filter.apply(string) ? string : this.generator.next();
      });
    }
    return new WordGenerator().generator;
  }

  static <A> ChooserGenerator<A> chooserGenerator_(long seed, List<A> list, A defaultValue) {
    return new ChooserGenerator<A>(seed, list, defaultValue);
  }

  static <A> ChooserGenerator<A> chooserGenerator_(List<A> list, A defaultValue) {
    return new ChooserGenerator<A>(list, defaultValue);
  }

  static RealistNameGenerator realistNameGenerator(long seed) {
    return new RealistNameGenerator(seed);
  }

  static Generator<LocalDate> dateGenerator(long seed, LocalDate min, LocalDate max) {
    final Generator<Integer> intGenerator = new IntGenerator(seed);
    return getLocalDateGenerator(min, max, intGenerator);
  }

  static Generator<LocalDate> dateGenerator(long seed, LocalDate min) {
    return dateGenerator(seed, min, LocalDate.now());
  }

  static Generator<LocalDate> dateGenerator(long seed) {
    return dateGenerator(seed, LocalDate.now().minusYears(100));
  }

  static Generator<LocalDate> dateGenerator(LocalDate min, LocalDate max) {
    final Generator<Integer> intGenerator = new IntGenerator();
    return getLocalDateGenerator(min, max, intGenerator);
  }

  static Generator<LocalDate> dateGenerator(LocalDate min) {
    return dateGenerator(min, LocalDate.now());
  }

  static Generator<LocalDate> dateGenerator() {
    return dateGenerator(LocalDate.now().minusYears(100));
  }

  static Generator<LocalDate> getLocalDateGenerator(LocalDate min, LocalDate max, Generator<Integer> intGenerator) {
    final long low = Date.from(min.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).getTime();
    final long high = Date.from(max.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).getTime();
    return intGenerator
        .flatMap(hByte -> intGenerator.map(lByte -> (((long) hByte) << 32) + lByte))
        .map(x -> LocalDateTime.ofInstant(Instant.ofEpochMilli(low + Math.abs(x) % (high - low)), ZoneOffset.UTC).toLocalDate());
  }

  static Generator<Person> personGenerator(long seed) {
    return getPersonGenerator(chooserGenerator(seed, EnglishNames.firstNames, "Error: empty list"), chooserGenerator(seed, EnglishNames.lastNames, "Error: empty list"), dateGenerator(seed));
  }

  static Generator<Person> personGenerator() {
    return getPersonGenerator(chooserGenerator(EnglishNames.firstNames, "Error: empty list"), chooserGenerator(EnglishNames.lastNames, "Error: empty list"), dateGenerator());
  }

  static Generator<Person> getPersonGenerator(Generator<String> firstNameGenerator,
                                              Generator<String> lastNameGenerator,
                                              Generator<LocalDate> dateGenerator) {
    return firstNameGenerator.flatMap(firstName -> lastNameGenerator
        .flatMap(lastName -> dateGenerator
            .map(birthDate -> new Person(firstName, lastName, birthDate))));
  }

  static <T> Generator<T> chooserGenerator(long seed,
                                           List<T> list, T defaultValue) {
    return getGenerator(list, intGenerator(seed), defaultValue);
  }

  static <T> Generator<T> chooserGenerator(List<T> list, T defaultValue) {
    return getGenerator(list, intGenerator(), defaultValue);
  }

//  static <T> Generator<T> getGenerator(List<T> list, Generator<Integer> intGenerator) {
//    return intGenerator.map(x -> Math.abs(x) % list.length())
//        .map(index -> list.getAt(index).getOrThrow());
//  }

  /**
   * Should return Result<Generator>
   */
  static <T> Generator<T> getGenerator(List<T> list, Generator<Integer> intGenerator, T defaultValue) {
    return intGenerator.map(x -> Math.abs(x) % list.length())
        .map(index -> list.getAt(index).getOrElse(defaultValue));
  }
}
