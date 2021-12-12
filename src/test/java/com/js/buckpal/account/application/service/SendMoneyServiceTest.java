package com.js.buckpal.account.application.service;

import com.js.buckpal.account.application.port.in.SendMoneyCommand;
import com.js.buckpal.account.application.port.out.AccountLock;
import com.js.buckpal.account.application.port.out.LoadAccountPort;
import com.js.buckpal.account.application.port.out.UpdateAccountStatePort;
import com.js.buckpal.account.domain.Account;
import com.js.buckpal.account.domain.Account.AccountId;
import com.js.buckpal.account.domain.Money;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

class SendMoneyServiceTest {

    private final LoadAccountPort loadAccountPort = mock(LoadAccountPort.class);
    private final AccountLock accountLock = mock(AccountLock.class);
    private final UpdateAccountStatePort updateAccountStatePort = mock(UpdateAccountStatePort.class);
    private final SendMoneyService sut = new SendMoneyService(
            loadAccountPort,
            accountLock,
            updateAccountStatePort,
            moneyTransferProperties());

    @Test
    void transactionSucceeds() {
        // given
        Account sourceAccount = givenSourceAccount();
        Account targetAccount = givenTargetAccount();

        givenWithdrawalWillSucceed(sourceAccount);
        givenDepositWillSucceed(targetAccount);

        Money money = Money.of(500L);

        SendMoneyCommand command = new SendMoneyCommand(
                sourceAccount.getId().get(),
                targetAccount.getId().get(),
                money);

        // when
        boolean success = sut.sendMoney(command);

        // then
        assertThat(success).isTrue();

        AccountId sourceAccountId = sourceAccount.getId().get();
        AccountId targetAccountId = targetAccount.getId().get();

        then(accountLock).should().lockAccount(eq(sourceAccountId));
        then(sourceAccount).should().withdraw(eq(money), eq(targetAccountId));
        then(accountLock).should().releaseAccount(eq(sourceAccountId));

        then(accountLock).should().lockAccount(eq(targetAccountId));
        then(targetAccount).should().deposit(eq(money), eq(sourceAccountId));
        then(accountLock).should().releaseAccount(eq(targetAccountId));

        thenAccountsHaveBeenUpdated(sourceAccountId, targetAccountId);
    }

    @Test
    void givenWithdrawalFails_thenOnlySourceAccountIsLockedAndReleased() {
        // given
        AccountId sourceAccountId = new AccountId(41L);
        Account sourceAccount = givenAnAccountWithId(sourceAccountId);

        AccountId targetAccountId = new AccountId(42L);
        Account targetAccount = givenAnAccountWithId(targetAccountId);

        givenWithdrawalWillFail(sourceAccount);
        givenDepositWillSucceed(targetAccount);

        SendMoneyCommand command = new SendMoneyCommand(sourceAccountId, targetAccountId, Money.of(300L));

        // when
        boolean success = sut.sendMoney(command);

        // then
        assertThat(success).isFalse();

        then(accountLock).should().lockAccount(eq(sourceAccountId));
        then(accountLock).should().releaseAccount(eq(sourceAccountId));
        then(accountLock).should(never()).lockAccount(eq(targetAccountId));
    }

    private Account givenSourceAccount() {
        return givenAnAccountWithId(new AccountId(41L));
    }

    private Account givenTargetAccount() {
        return givenAnAccountWithId(new AccountId(42L));
    }

    private Account givenAnAccountWithId(AccountId id) {
        Account account = mock(Account.class);

        given(account.getId())
                .willReturn(Optional.of(id));
        given(loadAccountPort.loadAccount(eq(account.getId().get()), any(LocalDateTime.class)))
                .willReturn(account);

        return account;
    }

    private void givenWithdrawalWillSucceed(Account account) {
        given(account.withdraw(any(Money.class), any(AccountId.class)))
                .willReturn(true);
    }


    private void givenWithdrawalWillFail(Account account) {
        given(account.withdraw(any(Money.class), any(AccountId.class)))
                .willReturn(false);
    }

    private void givenDepositWillSucceed(Account account) {
        given(account.deposit(any(Money.class), any(AccountId.class)))
                .willReturn(true);
    }

    private void thenAccountsHaveBeenUpdated(AccountId... accountIds) {
        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);

        then(updateAccountStatePort).should(times(accountIds.length))
                .updateActivities(accountCaptor.capture());

        List<AccountId> updatedAccountIds = accountCaptor.getAllValues()
                .stream()
                .map(Account::getId)
                .map(Optional::get)
                .collect(Collectors.toList());

        assertThat(updatedAccountIds).containsExactlyInAnyOrder(accountIds);
    }

    private MoneyTransferProperties moneyTransferProperties() {
        return new MoneyTransferProperties(Money.of(Long.MAX_VALUE));
    }
}