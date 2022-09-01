package com.blanckspace.usecases

import com.blanckspace.domain.utils.TestDataGenerator
import com.blankspace.domain.repository.BankingRepository
import com.blankspace.domain.usecase.GetUserInfo
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class GetUserInfoTest {

    private lateinit var getUserInfo: GetUserInfo

    @Mock
    private lateinit var bankingRepository: BankingRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getUserInfo = GetUserInfo(
            bankingRepository,
            Schedulers.trampoline(),
            Schedulers.trampoline()
        )
    }

    @Test
    fun test_getUserInfo_success() {
        val userInfo = TestDataGenerator.generateUserInfo()

        Mockito.`when`(bankingRepository.getUserInfo(userInfo.accountNumber))
            .thenReturn(Observable.just(userInfo))

        val testObserver = getUserInfo.buildUseCase(userInfo.accountNumber).test()

        Mockito.verify(bankingRepository, times(1))
            .getUserInfo(userInfo.accountNumber)

        testObserver.assertSubscribed()
            .assertValue { it == userInfo }
            .assertComplete()

    }

    @Test
    fun test_getUserInfo_error() {
        val userInfo = TestDataGenerator.generateUserInfo()
        val errorMsg = "ERROR OCCURRED"

        Mockito.`when`(bankingRepository.getUserInfo(userInfo.accountNumber))
            .thenReturn(Observable.error(Throwable(errorMsg)))

        val testObserver = getUserInfo.buildUseCase(userInfo.accountNumber).test()

        Mockito.verify(bankingRepository, times(1))
            .getUserInfo(userInfo.accountNumber)

        testObserver.assertSubscribed()
            .assertError { it.message?.equals(errorMsg) ?: false }
            .assertNotComplete()
    }

    @Test
    fun test_AccountUpgradeEligibility() {
        val upgradableUserInfo = TestDataGenerator.generateUpgradableUserInfo()
        assert(upgradableUserInfo.isEligibleForUpgrade)

        val userInfo = TestDataGenerator.generateUserInfo()
        assert(!userInfo.isEligibleForUpgrade)
    }

    @Test(expected = IllegalArgumentException::class)
    fun test_getUserInfoNoParameters_error() {
        val userInfo = TestDataGenerator.generateUserInfo()
        userInfo.accountNumber

        val testObserver = getUserInfo.buildUseCase().test()
        testObserver.assertSubscribed()
    }

}