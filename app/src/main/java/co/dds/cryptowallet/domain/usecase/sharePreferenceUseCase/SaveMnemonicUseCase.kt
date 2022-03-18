package co.dds.cryptowallet.domain.usecase.sharePreferenceUseCase

import co.dds.cryptowallet.domain.repository.SharePreferenceRepository

class SaveMnemonicUseCase(
    private val sharePreferenceRepository: SharePreferenceRepository
) {
    suspend operator fun invoke(mnemonic: String) {
        sharePreferenceRepository.saveMnemonic(mnemonic)
    }
}