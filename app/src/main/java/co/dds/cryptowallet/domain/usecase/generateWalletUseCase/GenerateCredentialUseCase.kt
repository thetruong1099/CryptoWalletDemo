package co.dds.cryptowallet.domain.usecase.generateWalletUseCase

import co.dds.cryptowallet.domain.repository.GenerateWalletRepository
import org.web3j.crypto.Credentials

class GenerateCredentialUseCase(
    private val generateWalletRepository: GenerateWalletRepository
) {
    suspend operator fun invoke(mnemonic: String, number: Int): Credentials =
        generateWalletRepository.generateCredential(mnemonic, number)
}