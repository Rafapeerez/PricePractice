package es.kairosds.pricepractice.application.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GenericUnexpectedException extends PriceException {
    public GenericUnexpectedException(Throwable cause) {
        super(cause);
    }
}
