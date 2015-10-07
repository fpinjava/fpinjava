package com.fpinjava.application.generator;


import com.fpinjava.common.List;

public class ListGenerator<T> implements Generator<List<T>> {

  private final Generator<T> elementGenerator;

  private final Generator<List<T>> listGenerator;

  protected ListGenerator(Generator<T> generator, int percentEmpty) {
    this(Generator.booleanGenerator(percentEmpty), generator, percentEmpty);
  }

  protected ListGenerator(long seed, Generator<T> generator, int percentEmpty) {
    this(Generator.booleanGenerator(seed, percentEmpty), generator, percentEmpty);
  }

  protected ListGenerator(Generator<Boolean> booleanGenerator, Generator<T> generator, int percentEmpty) {
    this.elementGenerator = generator;
    this.listGenerator = booleanGenerator.flatMap(empty -> empty
        ? List::list // = () -> List.list() = Generator<Empty List>
        : nonEmptyLists());
  }

  @Override
  public List<T> next() {
    return this.listGenerator.next();
  }

  private Generator<List<T>> nonEmptyLists() {
    return elementGenerator.flatMap(h -> listGenerator.map(t -> List.cons(h, t)));
  }
}
