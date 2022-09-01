package com.blankspace.domain.usecase

import com.blankspace.domain.entity.TransactionEntity
import com.blankspace.domain.qualifiers.Background
import com.blankspace.domain.qualifiers.Foreground
import com.blankspace.domain.repository.BankingRepository
import com.blankspace.domain.usecase.base.ObservableUseCase
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class FilterTransactions @Inject constructor(
    private val bankingRepository: BankingRepository,
    @Background backgroundScheduler: Scheduler,
    @Foreground foregroundScheduler: Scheduler
) : ObservableUseCase<List<TransactionEntity>, FilterTransactions.Params>(
    backgroundScheduler,
    foregroundScheduler
) {
    override fun generateObservable(input: Params?): Observable<List<TransactionEntity>> {
        if (input == null) {
            throw IllegalArgumentException("FilterTransactionsTask parameter can't be null")
        }
        return bankingRepository.getFilteredTransactions(
            userIdentifier = input.userIdentifier,
            credit = input.credit,
            debit = input.debit,
            flagged = input.flagged
        )
    }

    data class Params(
        val userIdentifier: String,
        val credit: Boolean,
        val debit: Boolean,
        val flagged: Boolean
    )

}