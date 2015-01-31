package com.fpinjava.makingjavafunctional.listing03_02;

import java.util.regex.Pattern;

import com.fpinjava.common.Function;

public class EmailValidation {
  
  final Pattern emailPattern =
      Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");

  final Function<String, Boolean> emailChecker = s -> 
                                  emailPattern.matcher(s).matches();

  void testMail(String email) {
    if (emailChecker.apply(email)) {
      sendVerificationMail(email);   
    } else {
      logError("email " + email + " is invalid.");
    }
  }

  private void logError(String s) {
    System.err.println("Error message logged: " + s);
  }

  private void sendVerificationMail(String s) {
    System.out.println("Mail sent to " + s);
  }
}
