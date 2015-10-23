package com.fpinjava.makingjavafunctional.exercise03_02;

import static com.fpinjava.makingjavafunctional.exercise03_01.Result.failure;
import static com.fpinjava.makingjavafunctional.exercise03_01.Result.success;
import static com.fpinjava.makingjavafunctional.listing03_08.Case.match;
import static com.fpinjava.makingjavafunctional.listing03_08.Case.mcase;
import static org.junit.Assert.*;

import java.util.regex.Pattern;

import com.fpinjava.common.Function;
import com.fpinjava.makingjavafunctional.exercise03_01.Effect;
import com.fpinjava.makingjavafunctional.exercise03_01.Result;

import org.junit.Test;

public class CaseTest {

  /* Uncomment this these test once the exercise is solved
  @Test
  public void testMcaseSupplierOfBooleanSupplierOfResultOfT() {
    Case<Integer> c1 = Case.mcase(() -> true, () -> Result.success(4));
    assertTrue(c1._1.get());
    Wrapper<Integer> success1 = new Wrapper<>();
    Wrapper<String> failure1 = new Wrapper<>();
    c1._2.get().bind(x -> success1.value = x, y -> failure1.value = y);
    assertEquals(Integer.valueOf(4), success1.value);
    Case<Integer> c2 = Case.mcase(() -> false, () -> Result.failure("failure"));
    assertFalse(c2._1.get());
    Wrapper<Integer> success2 = new Wrapper<>();
    Wrapper<String> failure2 = new Wrapper<>();
    c2._2.get().bind(x -> success2.value = x, y -> failure2.value = y);
    assertEquals("failure", failure2.value);
  }

  @Test
  public void testMcaseSupplierOfResultOfT() {
    Case<Integer> c1 = Case.mcase(() -> Result.success(4));
    assertTrue(c1._1.get());
    Wrapper<Integer> success1 = new Wrapper<>();
    Wrapper<String> failure1 = new Wrapper<>();
    c1._2.get().bind(x -> success1.value = x, y -> failure1.value = y);
    assertEquals(Integer.valueOf(4), success1.value);
  }
//*/
  @Test
  public void testMatch() {
    Pattern emailPattern =
        Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");

    Wrapper<String> successWrapper = new Wrapper<>();
    Wrapper<String> failureWrapper = new Wrapper<>();

    Effect<String> success = s -> successWrapper.value = s;

    Effect<String> failure = s -> failureWrapper.value = s;


    Function<String, Result<String>> emailChecker = s -> match(
        mcase(() -> success(s)),
        mcase(() -> s == null, () -> failure("email must not be null.")),
        mcase(() -> s.length() == 0, () ->
                                     failure("email must not be empty.")),
        mcase(() -> !emailPattern.matcher(s).matches(), () ->
                                     failure("email " + s + " is invalid."))
    );

    emailChecker.apply("this.is@my.email").bind(success, failure);
    assertEquals("email this.is@my.email is invalid.", failureWrapper.value);
    emailChecker.apply(null).bind(success, failure);
    assertEquals("email must not be null.", failureWrapper.value);
    emailChecker.apply("").bind(success, failure);
    assertEquals("email must not be empty.", failureWrapper.value);
    emailChecker.apply("john.doe@acme.com").bind(success, failure);
    assertEquals("john.doe@acme.com", successWrapper.value);
  }

  private static class Wrapper<T> {
    public T value;
  }
}
