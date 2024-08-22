package es.kairosds.pricepractice.infrastructure.rest.handler;

import es.kairosds.pricepractice.application.exceptions.BadRequestException;
import es.kairosds.pricepractice.application.exceptions.GenericUnexpectedException;
import es.kairosds.pricepractice.application.exceptions.MissingDataException;
import es.kairosds.pricepractice.application.exceptions.PriceException;
import es.kairosds.pricepractice.infrastructure.rest.dto.ErrorCodeDTO;
import es.kairosds.pricepractice.infrastructure.rest.dto.ErrorDTO;
import lombok.RequiredArgsConstructor;

import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.http.ResponseEntity.badRequest;

@RestControllerAdvice
@RequiredArgsConstructor
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private final PriceExceptionMapper priceExceptionMapper;

    private static final String BAD_REQUEST_ERR_FORMATTER = "%s: %s";

    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request
    ) {
        final String extraInfo = Stream.concat(
            ex.getBindingResult().getFieldErrors().stream()
                .map(err -> BAD_REQUEST_ERR_FORMATTER.formatted(err.getField(), err.getDefaultMessage())),
            ex.getBindingResult().getGlobalErrors().stream()
                .map(err -> BAD_REQUEST_ERR_FORMATTER.formatted(err.getObjectName(), err.getDefaultMessage()))
        ).collect(Collectors.joining(" | ", "", ""));
        return badRequest().body(buildError(new BadRequestException(extraInfo), ErrorCodeDTO.BAD_REQUEST));
    }

    protected ResponseEntity<Object> handleHttpMessageNotReadable(
        final HttpMessageNotReadableException ex,
        final HttpHeaders headers,
        final HttpStatus status,
        final WebRequest request
    ) {
        return badRequest().body(buildError(new BadRequestException(ex.getMessage()), ErrorCodeDTO.BAD_REQUEST));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
        MissingServletRequestParameterException ex, HttpHeaders headers, 
            HttpStatusCode status, WebRequest request) {

        String errorMessage = "missing required parameter: " + ex.getParameterName();
        ErrorDTO errorDTO = buildError(new MissingDataException(errorMessage), ErrorCodeDTO.BAD_REQUEST);
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDTO> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String name = ex.getName();
        String type = ex.getRequiredType().getSimpleName();
        Object value = ex.getValue();
        String message = String.format("Parameter '%s' must be of type %s , but received: %s", name, type, value);

        ErrorDTO errorDTO = buildError(new BadRequestException(message), ErrorCodeDTO.BAD_REQUEST);
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PriceException.class)
    protected ResponseEntity<ErrorDTO> priceExceptionHandler(PriceException ex) {
        final Pair<ErrorCodeDTO, HttpStatus> errorInfo = priceExceptionMapper.get(ex.getClass());
        return new ResponseEntity<>(buildError(ex, errorInfo.getFirst()), errorInfo.getSecond());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorDTO> globalExceptionHandler(Exception exception) {
        return priceExceptionHandler(new GenericUnexpectedException(exception));
    }

    private ErrorDTO buildError(
        final Exception ex,
        final ErrorCodeDTO errorCode
    ) {
        return ErrorDTO.builder()
            .code(errorCode)
            .message(ex.getMessage())
            .build();
    }
}
