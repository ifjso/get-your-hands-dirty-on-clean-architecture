package com.js.buckpal.account.application.port.in;

import com.js.buckpal.account.domain.Account.AccountId;
import com.js.buckpal.account.domain.Money;

public interface GetAccountBalanceQuery {

    Money getAccountBalance(AccountId accountId);
}
