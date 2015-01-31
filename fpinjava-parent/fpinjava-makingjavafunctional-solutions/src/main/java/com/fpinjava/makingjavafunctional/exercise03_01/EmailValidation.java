package com.fpinjava.makingjavafunctional.exercise03_01;

import static com.fpinjava.makingjavafunctional.exercise03_01.Result.failure;
import static com.fpinjava.makingjavafunctional.exercise03_01.Result.success;

import java.util.regex.Pattern;

import com.fpinjava.common.Function;
import com.fpinjava.makingjavafunctional.exercise03_01.Effect;
import com.fpinjava.makingjavafunctional.exercise03_01.Result;

public class EmailValidation {

  static Pattern emailPattern =
      Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");

  static Function<String, Result<String>> emailChecker = s -> {
    if (s == null) {
      return failure("email must not be null");
    } else if (s.length() == 0) {
      return failure("email must not be empty");
    } else if (emailPattern.matcher(s).matches()) {
      return success(s);
    } else {
      return failure("email " + s + " is invalid.");
    }
  };

  public static void main(String... args) {
    emailChecker.apply("this.is@my.email").bind(success, failure);
    emailChecker.apply(null).bind(success, failure);
    emailChecker.apply("").bind(success, failure);
    emailChecker.apply("john.doe@acme.com").bind(success, failure);
  }

  static Effect<String> success = s -> 
                             System.out.println("Mail sent to " + s);
  
  static Effect<String> failure = s -> 
                    System.err.println("Error message logged: " + s);
}