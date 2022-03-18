package co.dds.cryptowallet.domain.usecase.nftTokenRoomDbUseCase

import co.dds.cryptowallet.domain.model.localDatabase.NFTModel
import co.dds.cryptowallet.domain.repository.NftTokenRoomDBRepository

class InsertNftTokenUseCase(
    private val nftTokenRoomDBRepository: NftTokenRoomDBRepository
) {
    suspend operator fun invoke(nftModel: NFTModel) =
        nftTokenRoomDBRepository.insertNftToken(nftModel)
}