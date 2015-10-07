package com.fpinjava.application.generator;


import com.fpinjava.common.List;

public class RealistNameGenerator implements Generator<String> {

  private static List<String> consonantList = List.list("b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "p", "r", "s", "t", "v", "w", "x", "z", "bl", "br", "ch", "ck", "cl", "cr", "dr", "fl", "fr", "gh", "gl", "gr", "ng", "ph", "pl", "pr", "qu", "sc", "sh", "sk", "sl", "sm", "sn", "sp", "st", "sw", "th", "tr", "tw", "wh", "wr", "nth", "sch", "scr", "shr", "spl", "spr", "squ", "str", "thr");
  private static List<String> vowelList = List.list("a", "e", "i", "o", "u", "ai", "au", "aw", "ay", "ea", "ee", "ei", "eu", "ew", "ey", "ie", "oi", "oo", "ou", "ow", "oy");

  private final IntGenerator intGenerator;

  private final ListGenerator<Integer> listGenerator;

  private final ChooserGenerator<String> consonantGenerator;

  private final ChooserGenerator<String> vowelGenerator;

  public Generator<Integer> choose(int low, int high) {
    return intGenerator.map(x -> low + x % (high - low));
  }

  protected RealistNameGenerator(long seed) {
    this.intGenerator = new IntGenerator(seed);
    this.listGenerator = new ListGenerator<Integer>(seed, this.intGenerator, 1)  {

      @Override
      public List<Integer> next() {
        List<Integer> list = super.next();
        return list.length() > 1 && list.length() < 4
            ? list
            : next();
      }
    };
    this.consonantGenerator = Generator.chooserGenerator_(seed, consonantList);
    this.vowelGenerator = Generator.chooserGenerator_(seed, vowelList);
  }

  protected RealistNameGenerator() {
    this.intGenerator = new IntGenerator();
    this.listGenerator = new ListGenerator<Integer>(this.intGenerator, 1)  {

      @Override
      public List<Integer> next() {
        List<Integer> list = super.next();
        return list.length() > 1 && list.length() < 4
            ? list
            : next();
      }
    };
    this.consonantGenerator = Generator.chooserGenerator_(consonantList);
    this.vowelGenerator = Generator.chooserGenerator_(vowelList);
  }

  @Override
  public String next() {
    List<Integer> ints = this.listGenerator.next();
    return ints.map(x -> this.consonantGenerator.next() + this.vowelGenerator.next()).foldLeft("", x -> y -> x + y);
  }
}
