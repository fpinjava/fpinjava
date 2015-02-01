package com.fpinjava.recursion.listing04_02;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.recursion.listing04_01.TailCall;
import com.fpinjava.recursion.listing04_04.Standalone;

public class AddTest {

  @Test
  public void test() {
    TailCall<Integer> tailCall = Add.add(3, 100000000);
    while(tailCall .isSuspend()) {
      tailCall = tailCall.resume();
    }
    assertEquals(Integer.valueOf(100000003), tailCall.eval());
  }

}
