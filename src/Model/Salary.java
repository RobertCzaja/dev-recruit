package Model;

import org.javamoney.moneta.Money;

public class Salary {
    private final Money from;
    private final Money to;

    public Salary(
            Number from,
            Number to,
            String currency
    ) {
        this.from = Money.of(from, currency);
        this.to = Money.of(to, currency);
    }
}
