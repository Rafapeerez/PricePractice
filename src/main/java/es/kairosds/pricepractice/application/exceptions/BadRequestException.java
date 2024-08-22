package es.kairosds.pricepractice.application.exceptions;

public class BadRequestException extends PriceException {

    public BadRequestException(String info) {
        super("Bad request, " + info);
    }

}
