package properties;

import com.fpinjava.common.List;
import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

public class ListIntegerGenerator extends Generator<List<Integer>> {

  public ListIntegerGenerator(Class<List<Integer>> type) {
    super(type);
  }

  public ListIntegerGenerator(java.util.List<Class<List<Integer>>> types) {
    super(types);
  }

  @Override
  public List<Integer> generate(SourceOfRandomness random, GenerationStatus status) {
    /*-
     * For the first parameter of the fill method, we must use only positive 
     * numbers. Else we will have two kinds of troubles: 
     * 
     * 1) If the fill method is safe (returning empty list for negative values), 
     * half of our lists will be empty.
     * 
     * 2) If the fill method is not safe (testing for n = 0 instead of n <= 0, 
     * the fill method will never return.
     */
    return List.fill(Math.abs(random.nextInt() % 50), () -> random.nextInt());
  }

}
