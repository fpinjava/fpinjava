package com.fpinjava.application.listing15_04;


import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Result;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Properties;

public class PropertyReader {

  private final Result<Properties> properties;

  private final String source;

  private PropertyReader(Result<Properties> properties, String source) {
    this.properties = properties;
    this.source = source;
  }

  public Result<String> getAsString(String name) {
    return properties.flatMap(props -> getProperty(props, name));
  }

  public Result<Integer> getAsInteger(String name) {
    Result<String> rString = properties.flatMap(props -> getProperty(props, name));
    return rString.flatMap(x -> {
      try {
        return Result.success(Integer.parseInt(x));
      } catch (NumberFormatException e) {
        return Result.failure(String.format("Invalid value while parsing property %s: %s", name, x));
      }
    });
  }

  public <T> Result<List<T>> getAsList(String name, Function<String, T> f) {
    Result<String> rString = properties.flatMap(props -> getProperty(props, name));
    return rString.flatMap(s -> {
      try {
        return Result.success(List.fromSeparatedString(s, ',').map(f));
      } catch (NumberFormatException e) {
        return Result.failure(String.format("Invalid value while parsing property %s: %s", name, s));
      }
    });
  }

  public Result<List<Integer>> getAsIntegerList(String name) {
    return getAsList(name, Integer::parseInt);
  }

  public Result<List<Double>> getAsDoubleList(String name) {
    return getAsList(name, Double::parseDouble);
  }

  public Result<List<Boolean>> getAsBooleanList(String name) {
    return getAsList(name, Boolean::parseBoolean);
  }

  public Result<List<String>> getAsStringList(String name) {
    return getAsList(name, Function.identity());
  }

  public <T> Result<T> getAsType(final Function<String, Result<T>> function, final String name) {
    Result<String> rString = properties.flatMap(props -> getProperty(props, name));
    return rString.flatMap(s -> {
      try {
        return function.apply(s);
      } catch (Exception e) {
        return Result.failure(String.format("Invalid value while parsing property %s: %s", name, s));
      }
    });
  }

  public <T extends Enum<?>> Result<T> getAsEnum(final String parameterName, final Class<T> enumClass) {

    Function<String, Result<T>> f = t -> {
      try {
        T constant = enumClass.getEnumConstants()[0];
        @SuppressWarnings("unchecked")
        T value = (T) Enum.valueOf(constant.getClass(), t);
        return Result.success(value);
      } catch (Exception e) {
        return Result.failure(String.format("Error parsing property %s: value %s can't be parsed to %s.", t, parameterName, enumClass.getName()));
      }
    };

    return getAsType(f, parameterName);
  }

  public static String toPropertyString(String s) {
    return s.replace(";", "\n");
  }

  public Result<String> getAsPropertyString(String propertyName) {
    return getAsString(propertyName).map(PropertyReader::toPropertyString);
  }

  private Result<String> getProperty(Properties properties, String name) {
    return Result.of(properties.getProperty(name)).mapFailure(String.format("Property \"%s\" no found in %s", name, this.source));
  }

  private static Result<Properties> readPropertiesFromFile(String configFileName) {
    try (InputStream inputStream = PropertyReader.class.getClassLoader().getResourceAsStream(configFileName)) {
      Properties properties = new Properties();
      properties.load(inputStream);
      return Result.of(properties);
    } catch (NullPointerException e) {
      return Result.failure(String.format("File %s not found in classpath", configFileName));
    } catch (IOException e) {
      return Result.failure(String.format("IOException reading classpath resource %s", configFileName));
    } catch (Exception e) {
      return Result.failure(String.format("Exception reading classpath resource %s", configFileName), e);
    }
  }

  private static Result<Properties> readPropertiesFromString(String propString) {
    try (Reader reader = new StringReader(propString)) {
      Properties properties = new Properties();
      properties.load(reader);
      return Result.of(properties);
    } catch (Exception e) {
      return Result.failure(String.format("Exception reading property string %s", propString), e);
    }
  }

  public static PropertyReader filePropertyReader(String fileName) {
    return new PropertyReader(readPropertiesFromFile(fileName), String.format("File: %s", fileName));
  }

  public static PropertyReader stringPropertyReader(String propString) {
    return new PropertyReader(readPropertiesFromString(propString), String.format("String: %s", propString));
  }
}
