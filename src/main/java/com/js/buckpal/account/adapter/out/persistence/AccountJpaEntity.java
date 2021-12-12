package com.js.buckpal.account.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account")
@Getter
@AllArgsConstructor
@NoArgsConstructor
class AccountJpaEntity {

    @Id
    @GeneratedValue
    private Long id;
}
