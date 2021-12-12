package com.js.buckpal.account.domain;

import com.js.buckpal.account.domain.Account.AccountId;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.js.buckpal.common.ActivityTestData.defaultActivity;
import static org.assertj.core.api.Assertions.assertThat;

class ActivityWindowTest {

    @Test
    void calculateStartTimestamp() {
        ActivityWindow activityWindow = new ActivityWindow(
                defaultActivity().withTimestamp(startDate()).build(),
                defaultActivity().withTimestamp(inBetweenDate()).build(),
                defaultActivity().withTimestamp(endDate()).build());

        assertThat(activityWindow.getStartTimestamp()).isEqualTo(startDate());
    }

    @Test
    void calculateEndTimestamp() {
        ActivityWindow activityWindow = new ActivityWindow(
                defaultActivity().withTimestamp(startDate()).build(),
                defaultActivity().withTimestamp(inBetweenDate()).build(),
                defaultActivity().withTimestamp(endDate()).build());

        assertThat(activityWindow.getEndTimestamp()).isEqualTo(endDate());
    }

    @Test
    void calculateBalance() {
        AccountId account1 = new AccountId(1L);
        AccountId account2 = new AccountId(2L);

        ActivityWindow activityWindow = new ActivityWindow(
                defaultActivity()
                        .withSourceAccountId(account1)
                        .withTargetAccountId(account2)
                        .withMoney(Money.of(999L))
                        .build(),
                defaultActivity()
                        .withSourceAccountId(account1)
                        .withTargetAccountId(account2)
                        .withMoney(Money.of(1L))
                        .build(),
                defaultActivity()
                        .withSourceAccountId(account2)
                        .withTargetAccountId(account1)
                        .withMoney(Money.of(500L))
                        .build());

        assertThat(activityWindow.calculateBalance(account1)).isEqualTo(Money.of(-500L));
    }

    private LocalDateTime startDate() {
        return LocalDateTime.of(2021, 12, 10, 0, 0);
    }

    private LocalDateTime inBetweenDate() {
        return LocalDateTime.of(2021, 12, 11, 0, 0);
    }

    private LocalDateTime endDate() {
        return LocalDateTime.of(2021, 12, 12, 0, 0);
    }
}
