package com.js.buckpal.account.domain;

import java.math.BigInteger;
import lombok.Value;

@Value
public class Money {

    public static Money ZERO = Money.of(0L);

    private final BigInteger amount;

    public boolean isPositiveOrZero() {
        return this.amount.compareTo(BigInteger.ZERO) >= 0;
    }

    public static Money of(long value) {
        return new Money(BigInteger.valueOf(value));
    }

    public static Money add(Money a, Money b) {
        return new Money(a.amount.add(b.amount));
    }

    public Money negate() {
        return new Money(this.amount.negate());
    }
}
