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

import com.fpinjava.common.List;
import com.pholser.junit.quickcheck.ForAll;
import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.generator.java.util.RFC4122.Version5;

@RunWith(Theories.class)
public class PropertyTest0 {

//  @Test
//  public void testTake() {
//    assertEquals(List.list(1, 2, 3, 4, 5).take(2), List.list(1, 2));
//  }
//    
//  /*
//   * First we must create take and takeWhile in List, as well as equals (and hashcode)
//   * We should also add a memoized length in List
//   */
//  @Test
//  public void testTakeWhile() {
//    assertEquals(List.list(1, 2, 3, 4, 5).takeWhile(x -> x < 3), List.list(1, 2));
//  }

  @DataPoint
  public static List<Integer> a = List.list(1, 2, 3, 4, 5);
  
  @DataPoint
  public static List<String> e = List.list("1", "2", "3", "4", "5", "6", "7", "8");

  @DataPoints
  public static Integer[] j = {0, 1, 2, 3 , 4, 5, 6, 7, 8, 9, 10};

  @Retention(RetentionPolicy.RUNTIME)
  @ParametersSuppliedBy(ListSupplier.class)
  public @interface AllLists {}

  @Retention(RetentionPolicy.RUNTIME)
  @ParametersSuppliedBy(IntegerSupplier.class)
  public @interface AllIntegers {}

  public static class ListSupplier extends ParameterSupplier {
    
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public java.util.List getValueSources(ParameterSignature signature) {
    
      //AllLists annotation = signature.getAnnotation(AllLists.class);
      //System.out.println("Just wanted to show that I can access it " + annotation);

     java.util.ArrayList result = new java.util.ArrayList();
     result.add(PotentialAssignment.forValue("List1", List.list("1", "2", "3", "4", "5", "6", "7", "8")));
     result.add(PotentialAssignment.forValue("List2", List.list(1, 2, 3, 4, 5, 6, 7)));
     result.add(PotentialAssignment.forValue("List3", List.list(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)));
    
     return result;
    }
   }

  public static class IntegerSupplier extends ParameterSupplier {
    
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public java.util.List getValueSources(ParameterSignature signature) {
    
     java.util.ArrayList result = new java.util.ArrayList();
     result.add(PotentialAssignment.forValue("Value1", 0));
     result.add(PotentialAssignment.forValue("Value2", 1));
     result.add(PotentialAssignment.forValue("Value3", 2));
     result.add(PotentialAssignment.forValue("Value1", 3));
     result.add(PotentialAssignment.forValue("Value2", 5));
     result.add(PotentialAssignment.forValue("Value2", 6));
     result.add(PotentialAssignment.forValue("Value3", 7));
     result.add(PotentialAssignment.forValue("Value3", 8));
     result.add(PotentialAssignment.forValue("Value3", 9));
     result.add(PotentialAssignment.forValue("Value3", 10));
    
     return result;
    }
   }

//  @Theory
//  public void imagineThisIsATest(@AllLists List<Integer> list, @AllIntegers int i) {
//    assumeTrue(i >= 0 && i < list.length());
//    assertEquals(list, List.concat(list.take(i), list.drop(i)));
//    System.out.println(i + " - " + list);
//  }

//  @Theory
//  public void testIntegers(@TestedOn(ints = { 2, 3, 4, 7, 13, 23, 42 }) int i) {
//   System.out.println(i);
//  }


  @SuppressWarnings({"unchecked", "rawtypes"})
  @Theory
  public void testTakeDropInteger(List list, @ForAll int i) {
    assumeTrue(i >= 0 && i < list.length());
    assertEquals(list, List.concat(list.take(i), list.drop(i)));
    System.out.println(i + " - " + list);
  }

}
