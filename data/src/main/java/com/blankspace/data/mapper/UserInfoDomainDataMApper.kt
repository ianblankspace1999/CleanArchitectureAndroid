package com.blankspace.data.mapper

import com.blankspace.data.model.UserInfoData
import com.blankspace.domain.entity.UserInfoEntity
import javax.inject.Inject

class UserInfoDomainDataMapper @Inject constructor(): Mapper<UserInfoEntity, UserInfoData> {
    override fun from(e: UserInfoData): UserInfoEntity {
        return UserInfoEntity(
            accountNumber = e.accountNumber,
            displayName = e.displayName,
            address = e.address,
            displayableJoinDate = e.displayableJoinDate,
            premiumCustomer = e.premiumCustomer,
            accountBalance = e.accountBalance,
            accountType = e.accountType,
            unbilledTransactionCount = e.unbilledTransactionCount
        )
    }

    override fun to(t: UserInfoEntity): UserInfoData {
        return UserInfoData(
            accountNumber = t.accountNumber,
            displayName = t.displayName,
            address = t.address,
            displayableJoinDate = t.displayableJoinDate,
            premiumCustomer = t.premiumCustomer,
            accountBalance = t.accountBalance,
            accountType = t.accountType,
            unbilledTransactionCount = t.unbilledTransactionCount
        )
    }
}