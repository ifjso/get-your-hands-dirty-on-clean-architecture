package com.js.buckpal.account.application.service;

import com.js.buckpal.account.domain.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component                  // TODO move config
public class MoneyTransferProperties {

    private Money maximumTransferThreshold = Money.of(1_000_000L);
}
