package com.js.buckpal.account.application.port.in;

public interface SendMoneyUseCase {

    boolean sendMoney(SendMoneyCommand sendMoneyCommand);
}
