package com.js.buckpal.account.domain;

import com.js.buckpal.account.domain.Account.AccountId;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Activity {

    private final ActivityId id;
    private final Account.AccountId ownerAccountId;
    private final Account.AccountId sourceAccountId;
    private final Account.AccountId targetAccountId;
    private final LocalDateTime timestamp;
    private final Money money;

    public Activity(AccountId ownerAccountId,
                    AccountId sourceAccountId,
                    AccountId targetAccountId,
                    LocalDateTime timestamp,
                    Money money) {
        this.id = null;
        this.ownerAccountId = ownerAccountId;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.timestamp = timestamp;
        this.money = money;
    }

    @Getter
    public static class ActivityId {

        private final Long value;

        public ActivityId(Long value) {
            this.value = value;
        }
    }
}
