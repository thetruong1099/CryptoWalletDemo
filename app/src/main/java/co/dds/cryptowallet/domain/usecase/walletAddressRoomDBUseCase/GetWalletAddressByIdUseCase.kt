package co.dds.cryptowallet.domain.usecase.walletAddressRoomDBUseCase

import co.dds.cryptowallet.domain.model.localDatabase.WalletAddressModel
import co.dds.cryptowallet.domain.repository.WalletAddressRepository

class GetWalletAddressByIdUseCase(
    private val walletAddressRepository: WalletAddressRepository
) {
    suspend operator fun invoke(id: Int): WalletAddressModel =
        walletAddressRepository.getWalletAddressById(id)
}