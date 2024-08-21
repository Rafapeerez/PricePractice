package es.kairosds.pricepractice.domain.vos;

public enum Currency {
    EUR("EUR");

    public final String value;

    private Currency(String currency) {
        this.value = currency;
    }

    public String getValue() {
        return value;
    }
}
