package it.gov.pagopa.pu.processecexutions.enums.ingestion.version;

import java.util.Arrays;

public class VersionEnumUtils {

  private VersionEnumUtils() {}

  public static <T extends Enum<T> & VersionEnum> T fromValue(Class<T> enumClass, String value) {
    return Arrays.stream(enumClass.getEnumConstants())
      .filter(v -> v.getValue().equals(value))
      .findFirst()
      .orElseThrow(() -> new IllegalArgumentException("Invalid value: " + value));
  }
}
