package com.blankspace.domain.usecase

import com.blankspace.domain.entity.TransactionEntity
import com.blankspace.domain.qualifiers.Background
import com.blankspace.domain.qualifiers.Foreground
import com.blankspace.domain.repository.BankingRepository
import com.blankspace.domain.usecase.base.CompletableUseCase
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject

class TransactionStatusUpdate @Inject constructor(
    private val bankingRepository: BankingRepository,
    @Background backgroundScheduler: Scheduler,
    @Foreground foregroundScheduler: Scheduler
) : CompletableUseCase<TransactionEntity>(
    backgroundScheduler,
    foregroundScheduler
) {

    override fun generateCompletable(input: TransactionEntity?): Completable {
        if (input == null) {
            throw IllegalArgumentException("TransactionStatusUpdaterTask parameter can't be null")
        }
        return bankingRepository.updateTransaction(input)
    }
}