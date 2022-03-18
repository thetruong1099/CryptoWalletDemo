package co.dds.cryptowallet.domain.usecase.transactionTokenUseCase

import co.dds.cryptowallet.domain.repository.TransactionTokenRepository
import java.math.BigDecimal

class GetGasPriceUseCase(private val transactionTokenRepository: TransactionTokenRepository) {
    suspend operator fun invoke(
        walletAddress: String,
        contractAddress: String,
        value: String
    ): BigDecimal = transactionTokenRepository.getGasPrice(walletAddress, contractAddress, value)
}