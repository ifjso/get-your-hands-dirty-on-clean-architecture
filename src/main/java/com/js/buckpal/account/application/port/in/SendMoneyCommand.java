package com.js.buckpal.account.application.port.in;

import com.js.buckpal.account.domain.Account.AccountId;
import com.js.buckpal.account.domain.Money;
import com.js.buckpal.common.SelfValidating;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SendMoneyCommand extends SelfValidating<SendMoneyCommand> {

    @NotNull
    private final AccountId sourceAccountId;

    @NotNull
    private final AccountId targetAccountId;

    @NotNull
    private final Money money;

    public SendMoneyCommand(AccountId sourceAccountId, AccountId targetAccountId, Money money) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.money = money;
        this.validateSelf();
    }
}
