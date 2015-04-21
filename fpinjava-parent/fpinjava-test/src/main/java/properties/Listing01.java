package properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.contrib.theories.Theories;
import org.junit.contrib.theories.Theory;
import org.junit.runner.RunWith;

import com.fpinjava.common.List;
import com.pholser.junit.quickcheck.ForAll;
import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

@RunWith(Theories.class)
public class Listing01 {

  @Theory  // #A
  public void testReverse(@From(ListIntegerGenerator.class)                  // #B
                          @ForAll(sampleSize = 100) List<Integer> list) {
    assertEquals(list, list.reverse().reverse());                               // #C
    assertEquals(list.headOption(), list.reverse().lastOption());               // #D
    assertFalse(list.equals(list.reverse()) && list.length() > 1);              // #E
  }

  public static class ListIntegerGenerator extends Generator<List<Integer>> {
  
    public ListIntegerGenerator(Class<List<Integer>> type) {
      super(type);
    }
    
    @Override
    public List<Integer> generate(SourceOfRandomness random, GenerationStatus status) {
      return List.fill(Math.abs(random.nextInt() % 50), () -> random.nextInt());
    }
  
  }

}
