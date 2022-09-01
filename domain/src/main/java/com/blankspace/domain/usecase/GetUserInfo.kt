package com.blankspace.domain.usecase

import io.reactivex.Observable
import io.reactivex.Scheduler
import com.blankspace.domain.entity.UserInfoEntity
import com.blankspace.domain.qualifiers.Background
import com.blankspace.domain.qualifiers.Foreground
import com.blankspace.domain.repository.BankingRepository
import com.blankspace.domain.usecase.base.ObservableUseCase

import javax.inject.Inject

class GetUserInfo @Inject constructor(
    private val bankingRepository: BankingRepository,
    @Background backgroundScheduler: Scheduler,
    @Foreground foregroundScheduler: Scheduler
) : ObservableUseCase<UserInfoEntity, String>(backgroundScheduler, foregroundScheduler) {

    override fun generateObservable(input: String?): Observable<UserInfoEntity> {
        if (input == null) {
            throw IllegalArgumentException("User identifier can't be null")
        }
        return bankingRepository.getUserInfo(input)
    }

}