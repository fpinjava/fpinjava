package properties;

import static org.junit.Assert.*;
import static org.junit.Assume.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.junit.Test;
import org.junit.contrib.theories.DataPoint;
import org.junit.contrib.theories.DataPoints;
import org.junit.contrib.theories.ParameterSignature;
import org.junit.contrib.theories.ParameterSupplier;
import org.junit.contrib.theories.ParametersSuppliedBy;
import org.junit.contrib.theories.PotentialAssignment;
import org.junit.contrib.theories.Theories;
import org.junit.contrib.theories.Theory;
import org.junit.contrib.theories.suppliers.TestedOn;
import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.ForAll;
import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.generator.java.util.RFC4122.Version5;

@RunWith(Theories.class)
public class PropertyTest3 {  

  @DataPoints
  public static Integer[] j = {0, 1, 2, 3 , 4, 5, 6, 7, 8, 9, 10};

  @Theory
  public void testAddition(@ForAll int a, @ForAll int b) {
    assumeTrue(a >= 0 && b < 50);
    assertEquals(a + b, b + a);
    System.out.println(a + " + " + b);
  }

}
