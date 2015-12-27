package com.fpinjava.application.generator;


import com.fpinjava.common.List;

public class ChooserGenerator<A> implements Generator<A> {

  private final List<A> list;

  private final IntGenerator intGenerator;

  private final Generator<A> generator;


  /**
   * Should return Result<Generator>
   */
  protected ChooserGenerator(long seed, List<A> list, A defaultValue) {
    this.list = list;
    this.intGenerator = new IntGenerator(seed);
    this.generator = intGenerator.map(x -> Math.abs(x) % list.length()).map(index -> list.getAt(index).getOrElse(defaultValue));
  }


  /**
   * Should return Result<Generator>
   */
  protected ChooserGenerator(List<A> list, A defaultValue) {
    this.list = list;
    this.intGenerator = new IntGenerator();
    this.generator = intGenerator.map(x -> Math.abs(x) % list.length()).map(index -> list.getAt(index).getOrElse(defaultValue));
  }

  @Override
  public A next() {
    return generator.next();
  }
}
