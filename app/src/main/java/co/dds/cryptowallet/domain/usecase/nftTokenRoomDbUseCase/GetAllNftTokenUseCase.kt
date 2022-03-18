package co.dds.cryptowallet.domain.usecase.nftTokenRoomDbUseCase

import androidx.lifecycle.LiveData
import co.dds.cryptowallet.domain.model.localDatabase.NFTModel
import co.dds.cryptowallet.domain.repository.NftTokenRoomDBRepository

class GetAllNftTokenUseCase(
    private val nftTokenRoomDBRepository: NftTokenRoomDBRepository
) {
    operator fun invoke(): LiveData<MutableList<NFTModel>> =
        nftTokenRoomDBRepository.getAllNftToken()
}