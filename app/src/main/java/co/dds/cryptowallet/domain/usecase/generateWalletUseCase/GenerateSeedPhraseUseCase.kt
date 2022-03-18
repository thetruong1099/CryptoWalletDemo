package co.dds.cryptowallet.domain.usecase.generateWalletUseCase

import co.dds.cryptowallet.domain.repository.GenerateWalletRepository

class GenerateSeedPhraseUseCase(
    private val generateWalletRepository: GenerateWalletRepository
) {
    suspend operator fun invoke() =
        generateWalletRepository.generateSeedPhrase()
}