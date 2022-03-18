package co.dds.cryptowallet.domain.usecase.transactionTokenUseCase

import co.dds.cryptowallet.domain.repository.TransactionTokenRepository
import org.web3j.crypto.Credentials

class SendTokenUseCase(
    private val transactionTokenRepository: TransactionTokenRepository
) {
    suspend operator fun invoke(
        credentials: Credentials,
        desAddress: String,
        amount: String
    ): Boolean = transactionTokenRepository.sendTokenEth(credentials, desAddress, amount)
}