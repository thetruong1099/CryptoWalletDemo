package co.dds.cryptowallet.domain.usecase.generateWalletUseCase

import co.dds.cryptowallet.domain.repository.GenerateWalletRepository

class GenerateCheckValidateMnemonicUseCase(
    private val generateWalletRepository: GenerateWalletRepository
) {
    suspend operator fun invoke(mnemonic: String): Boolean =
        generateWalletRepository.generateCheckValidateMnemonic(mnemonic)
}