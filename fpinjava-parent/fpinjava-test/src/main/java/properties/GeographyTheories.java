package properties;

import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.junit.Assume.assumeThat;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.junit.contrib.theories.Theories;
import org.junit.contrib.theories.Theory;
import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.ForAll;
import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

@RunWith(Theories.class)
public class GeographyTheories {
  @Theory
  public void northernHemisphere(@ForAll @From(Coordinates.class) Coordinate c) {

    assumeThat(c.latitude(), greaterThan(BigDecimal.ZERO));

    assertTrue(c.inNorthernHemisphere());
  }

  public static class Coordinate {
    private final BigDecimal latitude;
    private final BigDecimal longitude;

    public Coordinate(BigDecimal latitude, BigDecimal longitude) {
      // argument checks here...

      this.latitude = latitude;
      this.longitude = longitude;
    }

    public BigDecimal latitude() {
      return latitude;
    }

    public BigDecimal longitude() {
      return longitude;
    }

    public boolean inNorthernHemisphere() {
      return latitude.compareTo(BigDecimal.ZERO) > 0;
    }
  }

  public static class Coordinates extends Generator<Coordinate> {

    public Coordinates(Class<Coordinate> type) {
      super(type);
    }

    public Coordinates(List<Class<Coordinate>> types) {
      super(types);
    }

    @Override
    public Coordinate generate(SourceOfRandomness random, GenerationStatus status) {

      return new Coordinate(BigDecimal.valueOf(random.nextDouble(-90, 90))
          .setScale(6, RoundingMode.CEILING), BigDecimal.valueOf(
          random.nextDouble(-180, 180)).setScale(6, RoundingMode.CEILING));
    }
  }
}