package it.gov.pagopa.pu.processexecutions.config.rest;

import it.gov.pagopa.pu.processexecutions.dto.generated.ErrorFieldDTO;

import java.util.List;

public record PuErrorDTO(
  String category,
  String code,
  String message,
  List<ErrorFieldDTO> fields
) {
}
