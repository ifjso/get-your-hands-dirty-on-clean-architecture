package com.js.buckpal.account.application.service;

import com.js.buckpal.account.application.port.in.SendMoneyCommand;
import com.js.buckpal.account.application.port.in.SendMoneyUseCase;
import com.js.buckpal.account.application.port.out.AccountLock;
import com.js.buckpal.account.application.port.out.LoadAccountPort;
import com.js.buckpal.account.application.port.out.UpdateAccountStatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
public class SendMoneyService implements SendMoneyUseCase {

    private final LoadAccountPort loadAccountPort;
    private final AccountLock accountLock;
    private final UpdateAccountStatePort updateAccountStatePort;

    @Override
    public boolean sendMoney(SendMoneyCommand sendMoneyCommand) {
        return false;
    }
}
