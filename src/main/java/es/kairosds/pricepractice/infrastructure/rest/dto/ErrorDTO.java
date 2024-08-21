package es.kairosds.pricepractice.infrastructure.rest.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorDTO {

    private final ErrorCodeDTO code;
    private final String message;
}
