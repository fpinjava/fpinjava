package properties;


import static org.junit.Assume.assumeTrue;
import org.junit.Test;
import org.junit.contrib.theories.DataPoint;
import org.junit.contrib.theories.Theories;
import org.junit.contrib.theories.Theory;
import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.ForAll;
import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.generator.java.util.RFC4122.Version5;



@RunWith(Theories.class)
public class TheorieTest {
 
 @DataPoint
 public static String a = "a";
 
 @DataPoint
 public static String b = "bb";
 
// @DataPoint
// public static String c = "ccc";
 
 @Theory
 public void stringTest(String x, String y) {
  assumeTrue(x.length() > 1);
 
  System.out.println(x + " " + y);
 }
}
