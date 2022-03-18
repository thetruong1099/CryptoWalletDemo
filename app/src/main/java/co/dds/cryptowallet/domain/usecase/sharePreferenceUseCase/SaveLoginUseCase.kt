package co.dds.cryptowallet.domain.usecase.sharePreferenceUseCase

import co.dds.cryptowallet.domain.repository.SharePreferenceRepository

class SaveLoginUseCase(
    private val sharePreferenceRepository: SharePreferenceRepository
) {
    suspend operator fun invoke(status: Boolean) {
        sharePreferenceRepository.saveStatusRememberMe(status)
    }
}