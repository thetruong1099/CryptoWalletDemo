package co.dds.cryptowallet.domain.usecase.sharePreferenceUseCase

import co.dds.cryptowallet.domain.repository.SharePreferenceRepository

class SetPassWordUseCase(
    private val sharePreferenceRepository: SharePreferenceRepository
) {
    suspend operator fun invoke(password: String) {
        sharePreferenceRepository.setPassword(password)
    }
}