package co.dds.cryptowallet.domain.usecase.sharePreferenceUseCase

import co.dds.cryptowallet.domain.repository.SharePreferenceRepository
import co.dds.cryptowallet.presentation.until.SharePreferenceLiveDataString

class GetMnemonicUseCase(
    private val sharePreferenceRepository: SharePreferenceRepository
) {
    operator fun invoke(): SharePreferenceLiveDataString = sharePreferenceRepository.getMnemonic()
}