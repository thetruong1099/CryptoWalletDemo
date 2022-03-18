package co.dds.cryptowallet.data.repository

import androidx.lifecycle.LiveData
import co.dds.cryptowallet.data.dataSource.roomDB.NFTTokenDao
import co.dds.cryptowallet.domain.model.localDatabase.NFTModel
import co.dds.cryptowallet.domain.repository.NftTokenRoomDBRepository

class NftTokenRoomDBRepositoryImp(
    private val nftTokenDao: NFTTokenDao
) : NftTokenRoomDBRepository {

    override suspend fun insertNftToken(nftModel: NFTModel) = nftTokenDao.addNftToken(nftModel)

    override suspend fun deleteNftToken(nftModel: NFTModel) = nftTokenDao.removeNftToken(nftModel)

    override fun getAllNftToken(): LiveData<MutableList<NFTModel>> =
        nftTokenDao.getAllNftToken()
}