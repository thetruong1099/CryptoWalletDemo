package co.dds.cryptowallet.domain.repository

import androidx.lifecycle.LiveData
import co.dds.cryptowallet.domain.model.localDatabase.NFTModel

interface NftTokenRoomDBRepository {
    suspend fun insertNftToken(nftModel: NFTModel)

    suspend fun deleteNftToken(nftModel: NFTModel)

    fun getAllNftToken(): LiveData<MutableList<NFTModel>>
}