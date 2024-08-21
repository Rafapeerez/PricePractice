package es.kairosds.pricepractice.domain.vos;

public class Amount {

    private Double value;

    public Amount(Double amount) {
        if (amount > 0) {
            this.value = amount;
        } else {
            throw new IllegalArgumentException("Amount can't be null");
        }
    }

    public Double getValue() {
        return value;
    }
}
