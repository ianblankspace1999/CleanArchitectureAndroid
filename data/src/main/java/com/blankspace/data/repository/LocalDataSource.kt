package com.blankspace.data.repository

import com.blankspace.data.model.TransactionData
import com.blankspace.data.model.UserInfoData
import io.reactivex.Completable
import io.reactivex.Observable

interface LocalDataSource {

    fun getUserInfo(identifier: String): Observable<UserInfoData>

    fun saveUserInfo(userInfoData: UserInfoData)

    fun getUserTransactions(userIdentifier: String, limit: Int): Observable<List<TransactionData>>

    fun saveUserTransactions(userIdentifier: String, transactions: List<TransactionData>)

    fun getTransaction(transactionId: String): Observable<TransactionData>

    fun getFilteredTransactions(
        userIdentifier: String,
        credit: Boolean?,
        debit: Boolean?,
        flagged: Boolean?
    ): Observable<List<TransactionData>>

    fun updateTransaction(transaction: TransactionData): Completable
}