package es.kairosds.pricepractice.application.exceptions;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PriceException extends RuntimeException {
    
    public static final String ERROR_ID_MSG = "An error occurred with id: {}";
    final UUID id = UUID.randomUUID();

    public PriceException() {
        super("");
        log.error(ERROR_ID_MSG, id);
        log.error("Error: ", this);
    }

    public PriceException(String info) {
        super(info);
        log.error(ERROR_ID_MSG, id);
        log.error(info, this);
    }

    public PriceException(Throwable cause) {
        super("", cause);
        log.error(ERROR_ID_MSG, id);
        log.error("Error: ", this);
        log.error("Cause: ", cause);
    }
}
