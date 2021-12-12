package com.js.buckpal.account.application.port.out;

import com.js.buckpal.account.domain.Account;

public interface UpdateAccountStatePort {

    void updateActivities(Account account);
}
