package co.dds.cryptowallet.domain.usecase.transactionTokenUseCase

import co.dds.cryptowallet.domain.repository.TransactionTokenRepository

class GetBalanceUseCase(private val transactionTokenRepository: TransactionTokenRepository) {
    suspend operator fun invoke(address: String): String =
        transactionTokenRepository.getBalance(address)
}