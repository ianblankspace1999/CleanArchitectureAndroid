package com.blankspace.domain.repository

import com.blankspace.domain.entity.TransactionEntity
import io.reactivex.Completable
import io.reactivex.Observable
import com.blankspace.domain.entity.UserInfoEntity

interface BankingRepository {

    fun getUserInfo(identifier: String): Observable<UserInfoEntity>

    fun getUserTransactions(userIdentifier: String, limit: Int): Observable<List<TransactionEntity>>

    fun getFilteredTransactions(
        userIdentifier: String,
        credit: Boolean,
        debit: Boolean,
        flagged: Boolean
    ): Observable<List<TransactionEntity>>

    fun updateTransaction(transaction: TransactionEntity): Completable
}
