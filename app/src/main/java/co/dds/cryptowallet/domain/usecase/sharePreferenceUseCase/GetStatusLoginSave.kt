package co.dds.cryptowallet.domain.usecase.sharePreferenceUseCase

import co.dds.cryptowallet.domain.repository.SharePreferenceRepository
import co.dds.cryptowallet.presentation.until.SharePreferenceLiveDataBoolean

class GetStatusLoginSave(
    private val sharePreferenceRepository: SharePreferenceRepository
) {
    operator fun invoke(): SharePreferenceLiveDataBoolean =
        sharePreferenceRepository.getStatusRememberMe()
}