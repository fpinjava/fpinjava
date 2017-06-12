## General questions

### Question: You haven’t defined xyz. What does it mean?

Always read the complete explanation before trying to answer the question. Many subjects imply cyclic dependencies between their parts. This kind of explanation must be read at least twice. Take the following example:


		public class Case<T> {
		
		  public static <T> Case<T> mcase(Supplier<Boolean> condition, Supplier<Result<T>> value) {
		    return new Case<>(condition, value);
		  }
		
		  public static <T> DefaultCase<T> mcase(Supplier<Result<T>> value) {
		    return new DefaultCase<>(() -> true, value);
		  }


You may wonder what this DefaultCase class is that we haven’t yet defined. But the DefaultCase definition is as follows:


		private static class DefaultCase<T> extends Case<T> {
		  ...


If we had defined `DefaultCase` first, you might have wondered what `Case` was. Reading the whole listing give all clues:

		public class Case<T> extends Tuple<Supplier<Boolean>, Supplier<Result<T>>> {
		
		  private Case(Supplier<Boolean> booleanSupplier, Supplier<Result<T>> resultSupplier) {
		    super(booleanSupplier, resultSupplier);
		  }
		
		  public static <T> Case<T> mcase(Supplier<Boolean> condition, Supplier<Result<T>> value) {
		    return new Case<>(condition, value);
		  }
		
		  public static <T> DefaultCase<T> mcase(Supplier<Result<T>> value) {
		    return new DefaultCase<>(() -> true, value);
		  }
		
		  private static class DefaultCase<T> extends Case<T> {
		
		    private DefaultCase(Supplier<Boolean> booleanSupplier, 
		                        Supplier<Result<T>> resultSupplier) {
		      super(booleanSupplier, resultSupplier);
		    }
		  }
		
		  @SafeVarargs
		  public static <T> Result<T> match(DefaultCase<T> defaultCase, Case<T>... matchers) {
		    for (Case<T> aCase : matchers) {
		      if (aCase._1.get()) return aCase._2.get();
		    }
		    return defaultCase._2.get();
		  }
		}

But because we might not always put the whole code in one part of the book, you should always refer to the accompanying code.

## Lambdas

### Question: How can you instantiate the Xyz interface without a concrete implementation?

For example, given the following interface:

		public interface Effect<T> {
		  void apply(T t);
		}

How can you instantiate it in the following?

    Effect<String> success = s -> System.out.println("Mail sent to " + s);


This is a lambda (new in Java 8) that is roughly equivalent to the following:

		static Effect<String> success = new Effect<String>() {
		
		  @Override
		  public void apply(String s) {
		    System.out.println("Mail sent to " + s);
		  }
		  
		};

This is described in chapter 2, along with the step-by-step process of converting the anonymous class notation to the lambda notation.

The two forms are equivalent only from the programmer’s point of view. Differences exist in the way the two forms are handled by the compiler.

### Question: In the following code, how can the compiler know that the lambda is to be compiled into a Supplier<...> (or whatever)?

		public static <T> DefaultCase<T> mcase(Supplier<Result<T>> value) {
		  return new DefaultCase<>(() -> true, value);
		}


The lambda `() -> true` is to be compiled into an anonymous class implementing a functional interface defining a single method taking no parameter (this is indicated by `()`) and returning a `boolean`.

Which interface to choose is indicated by the `DefaultCase` constructor:

		private DefaultCase(Supplier<Boolean> booleanSupplier, Supplier<Result<T>> resultSupplier) {
		  super(booleanSupplier, resultSupplier);
		}

The first parameter has to be a `Supplier<Boolean>`.

## Method references

Method references are syntactic sugar over lambdas. When the right part of a lambda is a call to a method that takes the left part as its argument, such as this:

		map(x -> change(x))
		flatMap(x -> a.change(x))

It can be replaced with a method reference:

		map(this::change)
		flatMap(a::change)

If the method is static, just replace this with the name of the class:

		forEach(x -> System.out.println(x))

This may be replaced with the following:

		forEach(System.out::println)

This works also for constructors:

		map(x -> new Person(x))

This may be replaced with the following:

		map(Person::new)

## Type annotations

All Java programmers know how to use class-level type annotations. This is the simplest use of generics. Class-level type annotations come in two categories.

### Class definition

A class can be defined with a type annotation. For example, a `Comparator` for strings will be defined as follows:

		public class String Comparator implements Comparator<String> {
		  ...
		}

### Class instantiation

When instantiating a class, we can use a type annotation such as this:

		List<String> list = new ArrayList<String>();

We can even use the diamond syntax (since Java 7) to simplify code typing:

		List<String> list = new ArrayList<>();

We don't need to repeat the type annotation on the right side because the compiler is able to infer it from the left side.

### Parameterized types

We can define `List` and `Comparator` in such ways because these classes are defined with type parameters:

		public class List<T> {
		  ...
		}
		
		public class Comparator<T> {
		  ...
		}

Here, `T` is a type variable. Using such variables make the classes generic. One definition of `List` can be used for all lists of different types by replacing `T` with the real type.

All this is well known. Methods and fields of such classes can be written using these types. For example, the `Pair<T, U>` class can be defined as follows:

		public class Pair<T, U> {
		
		  private final T left;
		
		  private final U right;
		
		  public Pair(T t, U u) {
		    left = t;
		    right = u;
		  }
		
		  public T getLeft() {
		    return left;
		  }
		
		  public U getRight() {
		    return right;
		  }
		}

You can see that the two fields (left and right), the constructor, and the two getters use the `T` type parameter.

### Method declaration type annotations

Methods must sometimes be parameterized with type parameters that aren’t in scope, which means that they weren’t declared with the class. This is most often the case for static methods, because the class type parameters aren’t available to such methods.

Static method declaration can be type annotated. For example, to define a static factory method for the `Pair` class, we can write the following:

		public static <T, U> Pair<T, U> create(T t, U u) {
		  return new Pair<>(t, u);
		}

Note the use of the diamond syntax, which is possible because Java is able to infer the type of `Pair` to create by looking at the method signature.

Instance methods can also be annotated. For example, we could define a method `mapLeft` to transform the left value of the pair by applying a function to it:

		public <V> Pair<V, U> mapLeft(Function<T, V> f) {
		  return new Pair<>(f.apply(left), right);
		}

### Method invocation type annotations

Type inference is mostly used by Java while dereferencing methods and fields. Sometimes Java isn’t able to infer the correct type, whether because it’s not possible (not enough information) or because Java type inference is weak (not enough information for the Java compiler). For example, a functional `List` class can define a method returning an empty list, such as `List.list()`. An empty list generally has a raw type, which means it’s not type annotated. The same empty list can be used for an empty list of `String` elements or an empty list of `Integer` elements. But to use it, we must give it a type. Java might be able to infer the type, as in the following:

		List<String> list = List.list();

In some cases, however, Java can’t infer the type, as in this code:

		public static <A> Function<A, Function<List<A>, A>> headOr() {
		  return foldRight().apply(constant());
		}

Here, Java can’t infer the type when calling the instance method `foldRight`. This example doesn’t compile, and the error message is as follows:

		Error:(113, 27) java: incompatible types: funclib.Function<java.lang.Object,funclib.Function<exercises.List<java.lang.Object>,java.lang.Object>> cannot be converted to funclib.Function<A,funclib.Function<exercises.List<A>,A>>

Java can't infer the type and is using `Object`. To fix this, we can annotate the method call:

		public static <A> Function<A, Function<List<A>, A>> headOr() {
		  return List.<A, A>foldRight().apply(constant());
		}

The method type annotations are to be written after the dot, so we must add the class name (or another reference for instance methods) in order to be able to use the dot. The following won’t compile:

		public static <A> Function<A, Function<List<A>, A>> headOr() {
		  return <A, A>foldRight().apply(constant());
		}

### Field type annotations

In imperative Java, it’s rarely (if ever?) useful to annotateion fields with a type. And by the way, field type annotations don’t exist in Java. In functional Java, we sometimes want to declare functions as static properties. So, if the functions must use type annotations, we cannot use fields to represent these properties. We must use methods instead (see below).

### What are properties?

As indicated in the Java documentation, a property is a characteristic of an object that can be read and/or written. In imperative Java, properties can often be written and read, so they’re generally implemented as private fields with public getters and setters.

In functional Java, properties are immutable, so we don’t need setters. Properties can be implemented as public final fields or private final fields with getters. Properties are generally initialized either in the constructor, or at declaration time with a literal value. In the later case, we’ll speak about a literal property, or a literal constant, because the property is immutable.

To define a function as a literal constant, we’d like to write the following:

		public static <T, U> Function<T, U> function = x -> …;

But we can’t do this because type-annotating fields is forbidden. The solution is to implement the constant as a method returning a literal value (defined in the body of the method):

		public static <T, U> Function<T, U> function() {
		  return x -> …;
		}

Here, `function()` isn’t the function. It’s a method returning the function. To use the function, instead of writing:

		T argument = ...
		U result = function.apply(argument);

we’ll write

		T argument = ...
		U result = function().apply(argument);
