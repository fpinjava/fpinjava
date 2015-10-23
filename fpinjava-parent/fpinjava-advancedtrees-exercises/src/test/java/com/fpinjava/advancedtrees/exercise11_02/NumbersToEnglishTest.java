package com.fpinjava.advancedtrees.exercise11_02;


import com.fpinjava.common.Tuple;
import org.junit.Test;

import static com.fpinjava.advancedtrees.exercise11_02.NumbersToEnglish.Locale;
import static com.fpinjava.advancedtrees.exercise11_02.NumbersToEnglish.convertGB;
import static com.fpinjava.advancedtrees.exercise11_02.NumbersToEnglish.convertUS;
import static com.fpinjava.advancedtrees.exercise11_02.NumbersToEnglish.convertUnder1000;
import static com.fpinjava.advancedtrees.exercise11_02.NumbersToEnglish.decompose;
import static com.fpinjava.advancedtrees.exercise11_02.NumbersToEnglish.thousands2String;
import static org.junit.Assert.assertEquals;

public class NumbersToEnglishTest {

  @Test
  public void testBillions() {
    assertEquals("", thousands2String.apply(Locale.US).apply(new Tuple<>(0,"thousand")));
    assertEquals("one thousand", thousands2String.apply(Locale.US).apply(new Tuple<>(1,"thousand")));
    assertEquals("three thousand", thousands2String.apply(Locale.US).apply(new Tuple<>(3,"thousand")));
    assertEquals("ten thousand", thousands2String.apply(Locale.US).apply(new Tuple<>(10,"thousand")));
    assertEquals("fifteen thousand", thousands2String.apply(Locale.US).apply(new Tuple<>(15,"thousand")));
    assertEquals("eighteen thousand", thousands2String.apply(Locale.US).apply(new Tuple<>(18,"thousand")));
    assertEquals("twenty million", thousands2String.apply(Locale.US).apply(new Tuple<>(20,"million")));
    assertEquals("twenty-one million", thousands2String.apply(Locale.US).apply(new Tuple<>(21,"million")));
    assertEquals("thirty-five million", thousands2String.apply(Locale.US).apply(new Tuple<>(35,"million")));
    assertEquals("fifty-nine million", thousands2String.apply(Locale.US).apply(new Tuple<>(59,"million")));
    assertEquals("ninety-three", thousands2String.apply(Locale.US).apply(new Tuple<>(93,"")));
    assertEquals("one hundred", thousands2String.apply(Locale.US).apply(new Tuple<>(100,"")));
    assertEquals("one hundred one", thousands2String.apply(Locale.US).apply(new Tuple<>(101,"")));
    assertEquals("one hundred and one", thousands2String.apply(Locale.GB).apply(new Tuple<>(101,"")));
    assertEquals("two hundred three billion", thousands2String.apply(Locale.US).apply(new Tuple<>(203,"billion")));
    assertEquals("two hundred and three billion", thousands2String.apply(Locale.GB).apply(new Tuple<>(203,"billion")));
    assertEquals("four hundred ten billion", thousands2String.apply(Locale.US).apply(new Tuple<>(410,"billion")));
    assertEquals("four hundred and ten billion", thousands2String.apply(Locale.GB).apply(new Tuple<>(410,"billion")));
    assertEquals("five hundred fifteen billion", thousands2String.apply(Locale.US).apply(new Tuple<>(515,"billion")));
    assertEquals("five hundred and fifteen billion", thousands2String.apply(Locale.GB).apply(new Tuple<>(515,"billion")));
    assertEquals("seven hundred sixty-five billion", thousands2String.apply(Locale.US).apply(new Tuple<>(765,"billion")));
    assertEquals("seven hundred and sixty-five billion", thousands2String.apply(Locale.GB).apply(new Tuple<>(765,"billion")));
  }

  @Test
  public void testDecompose() {
    assertEquals("[NIL]", decompose.apply(0).toString());
    assertEquals("[16, NIL]", decompose.apply(16).toString());
    assertEquals("[80, NIL]", decompose.apply(80).toString());
    assertEquals("[100, NIL]", decompose.apply(100).toString());
    assertEquals("[101, NIL]", decompose.apply(101).toString());
    assertEquals("[118, NIL]", decompose.apply(118).toString());
    assertEquals("[315, NIL]", decompose.apply(315).toString());
    assertEquals("[1, 1, NIL]", decompose.apply(1001).toString());
    assertEquals("[10, 1, NIL]", decompose.apply(1010).toString());
    assertEquals("[101, 1, NIL]", decompose.apply(1101).toString());
    assertEquals("[18, 2, NIL]", decompose.apply(2018).toString());
    assertEquals("[0, 0, 0, 1, NIL]", decompose.apply(1_000_000_000).toString());
    assertEquals("[16, 0, 0, 1, NIL]", decompose.apply(1_000_000_016).toString());
    assertEquals("[80, 2, 0, 2, NIL]", decompose.apply(2_000_002_080).toString());
    assertEquals("[100, 54, 0, 2, NIL]", decompose.apply(2_000_054_100).toString());
    assertEquals("[101, 327, 1, 2, NIL]", decompose.apply(2_001_327_101).toString());
    assertEquals("[468, 842, 183, 1, NIL]", decompose.apply(1_183_842_468).toString());
  }

  @Test
  public void testConvertUnder1000() {
    assertEquals("", convertUnder1000.apply(Locale.US).apply(0));
    assertEquals("sixteen", convertUnder1000.apply(Locale.US).apply(16));
    assertEquals("eighty", convertUnder1000.apply(Locale.US).apply(80));
    assertEquals("one hundred", convertUnder1000.apply(Locale.US).apply(100));
    assertEquals("one hundred one", convertUnder1000.apply(Locale.US).apply(101));
    assertEquals("one hundred and one", convertUnder1000.apply(Locale.GB).apply(101));
    assertEquals("one hundred eighteen", convertUnder1000.apply(Locale.US).apply(118));
    assertEquals("one hundred and eighteen", convertUnder1000.apply(Locale.GB).apply(118));
    assertEquals("three hundred fifteen", convertUnder1000.apply(Locale.US).apply(315));
    assertEquals("three hundred and fifteen", convertUnder1000.apply(Locale.GB).apply(315));
    assertEquals("", convertUnder1000.apply(Locale.US).apply(1000));
    assertEquals("one", convertUnder1000.apply(Locale.US).apply(1001));
    assertEquals("ten", convertUnder1000.apply(Locale.US).apply(1010));
    assertEquals("one hundred one", convertUnder1000.apply(Locale.US).apply(1101));
    assertEquals("one hundred and one", convertUnder1000.apply(Locale.GB).apply(1101));
    assertEquals("eighteen", convertUnder1000.apply(Locale.US).apply(2018));
    assertEquals("", convertUnder1000.apply(Locale.US).apply(1_000_000_000));
    assertEquals("fourteen", convertUnder1000.apply(Locale.US).apply(1_000_000_014));
    assertEquals("ninety-six", convertUnder1000.apply(Locale.US).apply(2_000_002_096));
    assertEquals("seven hundred", convertUnder1000.apply(Locale.US).apply(2_000_054_700));
    assertEquals("one hundred seventeen", convertUnder1000.apply(Locale.US).apply(2_001_327_117));
    assertEquals("one hundred and seventeen", convertUnder1000.apply(Locale.GB).apply(2_001_327_117));
    assertEquals("four hundred sixty-eight", convertUnder1000.apply(Locale.US).apply(1_183_842_468));
    assertEquals("four hundred and sixty-eight", convertUnder1000.apply(Locale.GB).apply(1_183_842_468));
  }

  @Test
  public void testConvert() {
    assertEquals("zero", convertUS.apply(0));
    assertEquals("sixteen", convertUS.apply(16));
    assertEquals("eighty", convertUS.apply(80));
    assertEquals("one hundred", convertUS.apply(100));
    assertEquals("one hundred one", convertUS.apply(101));
    assertEquals("one hundred and one", convertGB.apply(101));
    assertEquals("one hundred eighteen", convertUS.apply(118));
    assertEquals("one hundred and eighteen", convertGB.apply(118));
    assertEquals("three hundred fifteen", convertUS.apply(315));
    assertEquals("three hundred and fifteen", convertGB.apply(315));
    assertEquals("one thousand", convertUS.apply(1000));
    assertEquals("one thousand one", convertUS.apply(1001));
    assertEquals("one thousand ten", convertUS.apply(1010));
    assertEquals("one thousand one hundred one", convertUS.apply(1101));
    assertEquals("one thousand one hundred and one", convertGB.apply(1101));
    assertEquals("two thousand eighteen", convertUS.apply(2018));
    assertEquals("one billion", convertUS.apply(1_000_000_000));
    assertEquals("one billion sixteen", convertUS.apply(1_000_000_016));
    assertEquals("two billion eighty", convertUS.apply(2_000_000_080));
    assertEquals("two billion fifty-four thousand one hundred", convertUS.apply(2_000_054_100));
    assertEquals("two billion one million three hundred twenty-seven thousand one hundred one", convertUS.apply(2_001_327_101));
    assertEquals("two billion one million three hundred and twenty-seven thousand one hundred and one", convertGB.apply(2_001_327_101));
    assertEquals("one billion one hundred eighty-three million eight hundred forty-two thousand four hundred sixty-eight", convertUS.apply(1_183_842_468));
    assertEquals("one billion one hundred and eighty-three million eight hundred and forty-two thousand four hundred and sixty-eight", convertGB.apply(1_183_842_468));
    assertEquals("minus one billion one hundred eighty-three million eight hundred forty-two thousand four hundred sixty-eight", convertUS.apply(-1_183_842_468));
    assertEquals("minus one billion one hundred and eighty-three million eight hundred and forty-two thousand four hundred and sixty-eight", convertGB.apply(-1_183_842_468));
  }

}
