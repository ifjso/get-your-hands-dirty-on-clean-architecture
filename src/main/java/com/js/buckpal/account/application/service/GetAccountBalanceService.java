package com.js.buckpal.account.application.service;

import com.js.buckpal.account.application.port.in.GetAccountBalanceQuery;
import com.js.buckpal.account.application.port.out.LoadAccountPort;
import com.js.buckpal.account.domain.Account.AccountId;
import com.js.buckpal.account.domain.Money;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetAccountBalanceService implements GetAccountBalanceQuery {

    private final LoadAccountPort loadAccountPort;

    @Override
    public Money getAccountBalance(AccountId accountId) {
        return loadAccountPort.loadAccount(accountId, LocalDateTime.now())
            .calculateBalance();
    }
}
