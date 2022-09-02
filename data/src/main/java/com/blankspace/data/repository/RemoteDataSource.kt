package com.blankspace.data.repository

import com.blankspace.data.model.TransactionData
import com.blankspace.data.model.UserInfoData
import io.reactivex.Observable

interface RemoteDataSource {
    fun getUserInfo(identifier: String): Observable<UserInfoData>
    fun getUserTransactions(userIdentifier: String, limit: Int): Observable<List<TransactionData>>
}