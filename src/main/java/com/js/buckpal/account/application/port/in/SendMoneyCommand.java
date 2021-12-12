package com.js.buckpal.account.application.port.in;

import com.js.buckpal.account.domain.Account.AccountId;
import com.js.buckpal.account.domain.Money;
import com.js.buckpal.common.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@EqualsAndHashCode(callSuper = false)
public class SendMoneyCommand extends SelfValidating<SendMoneyCommand> {

    @NotNull
    private AccountId sourceAccountId;

    @NotNull
    private AccountId targetAccountId;

    @NotNull
    private Money money;

    public SendMoneyCommand(AccountId sourceAccountId, AccountId targetAccountId, Money money) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.money = money;
        this.validateSelf();
    }
}
