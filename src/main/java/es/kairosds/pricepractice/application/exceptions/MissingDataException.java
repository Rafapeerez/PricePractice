package es.kairosds.pricepractice.application.exceptions;

public class MissingDataException extends PriceException{
    public MissingDataException(String string) {
        super("Missing Data: " + string);
    }
}
