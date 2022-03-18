package co.dds.cryptowallet.domain.usecase.walletAddressRoomDBUseCase

import co.dds.cryptowallet.domain.repository.WalletAddressRepository

class GetPrivateKeyByIdUseCase(
    private val walletAddressRepository: WalletAddressRepository
) {
    suspend operator fun invoke(id: Int): String = walletAddressRepository.getPrivateKeyById(id)
}