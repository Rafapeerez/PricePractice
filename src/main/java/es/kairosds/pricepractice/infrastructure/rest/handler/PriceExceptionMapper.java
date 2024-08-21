package es.kairosds.pricepractice.infrastructure.rest.handler;

import org.springframework.stereotype.Component;

import es.kairosds.pricepractice.application.exceptions.BadRequestException;
import es.kairosds.pricepractice.application.exceptions.GenericUnexpectedException;
import es.kairosds.pricepractice.application.exceptions.MissingDataException;
import es.kairosds.pricepractice.application.exceptions.PriceException;
import es.kairosds.pricepractice.application.exceptions.PriceNotFoundException;
import es.kairosds.pricepractice.infrastructure.rest.dto.ErrorCodeDTO;

import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import java.util.Map;
import java.util.AbstractMap.SimpleImmutableEntry;


@Component
public class PriceExceptionMapper {
    public Pair<ErrorCodeDTO, HttpStatus> get(Class<? extends PriceException> priceExceptionClass) {
        return mappers.get(priceExceptionClass);
    }

    private final Map<Class<? extends PriceException>, Pair<ErrorCodeDTO, HttpStatus>> mappers = Map.ofEntries(
        entry(BadRequestException.class, ErrorCodeDTO.BAD_REQUEST, HttpStatus.BAD_REQUEST),
        entry(MissingDataException.class, ErrorCodeDTO.MISSING_DATA_ERROR ,HttpStatus.BAD_REQUEST),
        entry(PriceNotFoundException.class, ErrorCodeDTO.PRICE_NOT_FOUND, HttpStatus.NOT_FOUND),
        entry(GenericUnexpectedException.class, ErrorCodeDTO.GENERIC_UNEXPECTED_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR)
    );

    private SimpleImmutableEntry<Class<? extends PriceException>, Pair<ErrorCodeDTO, HttpStatus>> entry(
        Class<? extends PriceException> priceExeptionClass,
        ErrorCodeDTO errorCode,
        HttpStatus status
    ) {
        return new SimpleImmutableEntry<>(priceExeptionClass, Pair.of(errorCode, status));
    }
}
