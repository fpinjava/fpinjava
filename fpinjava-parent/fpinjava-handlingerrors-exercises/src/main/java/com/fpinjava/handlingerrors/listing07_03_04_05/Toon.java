package com.fpinjava.handlingerrors.listing07_03_04_05;


import com.fpinjava.handlingerrors.exercise07_04.Result;

public class Toon {
  private final String firstName;
  private final String lastName;
  private final Result<String> email;

  Toon(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = Result.failure(String.format("%s %s as no mail", firstName, lastName));
  }

  Toon(String firstName, String lastName, String email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = Result.success(email);
  }

  public Result<String> getEmail() {
    return email;
  }
}
