package co.dds.cryptowallet.domain.usecase.walletAddressRoomDBUseCase

import co.dds.cryptowallet.domain.model.localDatabase.WalletAddressModel
import co.dds.cryptowallet.domain.repository.WalletAddressRepository

class InsertWalletAddressUseCase(
    private val walletAddressRepository: WalletAddressRepository
) {
    suspend operator fun invoke(walletAddressModel: WalletAddressModel) =
        walletAddressRepository.insertWalletAddress(walletAddressModel)
}