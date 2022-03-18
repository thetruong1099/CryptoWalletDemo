package co.dds.cryptowallet.domain.usecase.walletAddressRoomDBUseCase

import co.dds.cryptowallet.domain.repository.WalletAddressRepository

class DeleteDatabaseUseCase(
    private val walletAddressRepository: WalletAddressRepository
) {
    suspend operator fun invoke() = walletAddressRepository.deleteDatabase()
}