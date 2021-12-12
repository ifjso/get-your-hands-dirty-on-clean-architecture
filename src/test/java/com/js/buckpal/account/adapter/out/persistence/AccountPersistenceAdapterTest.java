package com.js.buckpal.account.adapter.out.persistence;

import com.js.buckpal.account.domain.Account;
import com.js.buckpal.account.domain.Account.AccountId;
import com.js.buckpal.account.domain.ActivityWindow;
import com.js.buckpal.account.domain.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;

import static com.js.buckpal.common.AccountTestData.defaultAccount;
import static com.js.buckpal.common.ActivityTestData.defaultActivity;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({AccountPersistenceAdapter.class, AccountMapper.class})
class AccountPersistenceAdapterTest {

    @Autowired
    private AccountPersistenceAdapter sut;

    @Autowired
    private ActivityRepository activityRepository;

    @Test
    @Sql("AccountPersistenceAdapterTest.sql")
    void loadAccount() {
        // given
        Account account = sut.loadAccount(new AccountId(1L), LocalDateTime.of(2020, 12, 10, 0, 0));

        // when
        // then
        assertThat(account.getActivityWindow().getActivities()).hasSize(2);
        assertThat(account.calculateBalance()).isEqualTo(Money.of(500L));
    }

    @Test
    void updateActivities() {
        // given
        Account account = defaultAccount()
                .withBaselineBalance(Money.of(555L))
                .withActivityWindow(new ActivityWindow(
                        defaultActivity()
                                .withMoney(Money.of(1L))
                                .build()))
                .build();

        // when
        sut.updateActivities(account);

        // then
        assertThat(activityRepository.count()).isEqualTo(1);

        ActivityJpaEntity savedActivity = activityRepository.findAll().get(0);
        assertThat(savedActivity.getAmount()).isEqualTo(1);
    }
}