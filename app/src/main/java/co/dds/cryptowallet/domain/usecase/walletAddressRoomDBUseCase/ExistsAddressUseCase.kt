package co.dds.cryptowallet.domain.usecase.walletAddressRoomDBUseCase

import co.dds.cryptowallet.domain.repository.WalletAddressRepository

class ExistsAddressUseCase(
    private val walletAddressRepository: WalletAddressRepository
) {
    suspend operator fun invoke(address: String): Boolean =
        walletAddressRepository.existsAddress(address)
}