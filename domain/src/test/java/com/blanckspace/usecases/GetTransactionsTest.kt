package com.blanckspace.usecases

import com.blanckspace.domain.utils.TestDataGenerator
import com.blankspace.domain.repository.BankingRepository
import com.blankspace.domain.usecase.GetUserTransaction
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class GetTransactionsTest {

    private lateinit var getUserTransaction: GetUserTransaction
    @Mock
    lateinit var bankingRepository: BankingRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getUserTransaction = GetUserTransaction(
            bankingRepository,
            Schedulers.trampoline(),
            Schedulers.trampoline()
        )
    }

    @Test
    fun test_getUserTransactions_success() {
        val accountNumber = "1BFC9A38E6C7"
        val limit = 20
        val transactions = TestDataGenerator.generateTransactions()

        Mockito.`when`(bankingRepository.getUserTransactions(accountNumber, limit))
            .thenReturn(Observable.just(transactions))

        val testObserver = getUserTransaction.buildUseCase(
            GetUserTransaction.Params(
                accountNumber,
                limit
            )
        ).test()

        testObserver
            .assertSubscribed()
            .assertValue { it.containsAll(transactions) }
    }

    @Test
    fun test_getUserTransactions_error() {
        val accountNumber = "1BFC9A38E6C7"
        val limit = 20
        val errorMsg = "ERROR OCCURRED"

        Mockito.`when`(bankingRepository.getUserTransactions(accountNumber, limit))
            .thenReturn(Observable.error(Throwable(errorMsg)))

        val testObserver = getUserTransaction.buildUseCase(
            GetUserTransaction.Params(
                accountNumber,
                limit
            )
        ).test()

        testObserver
            .assertSubscribed()
            .assertError { it.message?.equals(errorMsg, false) ?: false }
            .assertNotComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun test_getUserTransactionsNoParams_error() {
        val testObserver = getUserTransaction.buildUseCase().test()
        testObserver.assertSubscribed()
    }

}