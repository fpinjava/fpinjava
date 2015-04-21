package properties;

import com.fpinjava.common.List;
import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

public class ListCharacterGenerator extends Generator<List<Character>> {

  public ListCharacterGenerator(Class<List<Character>> type) {
    super(type);
  }

  public ListCharacterGenerator(java.util.List<Class<List<Character>>> types) {
    super(types);
  }

  @Override
  public List<Character> generate(SourceOfRandomness random, GenerationStatus status) {
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
    return List.fill(Math.abs(random.nextInt() % 50), () -> random.nextChar((char) 97, (char) 123));
  }

}