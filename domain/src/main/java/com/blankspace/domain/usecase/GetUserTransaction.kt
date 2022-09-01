package com.blankspace.domain.usecase

import com.blankspace.domain.entity.TransactionEntity
import com.blankspace.domain.qualifiers.Background
import com.blankspace.domain.qualifiers.Foreground
import com.blankspace.domain.repository.BankingRepository
import com.blankspace.domain.usecase.base.ObservableUseCase
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class GetUserTransaction @Inject constructor(
    private val bankingRepository: BankingRepository,
    @Background backgroundScheduler: Scheduler,
    @Foreground foregroundScheduler: Scheduler
) : ObservableUseCase<List<TransactionEntity>, GetUserTransaction.Params>(
    backgroundScheduler,
    foregroundScheduler
) {
    override fun generateObservable(input: Params?): Observable<List<TransactionEntity>> {
        if (input == null) {
            throw IllegalArgumentException("GetUserTransactionsTask parameter can't be null")
        }
        return bankingRepository.getUserTransactions(input.identifier, input.limit)
    }

    data class Params(val identifier: String, val limit: Int)
}