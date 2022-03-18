package co.dds.cryptowallet.data.repository

import co.dds.cryptowallet.data.dataSource.roomDB.WalletAddressDao
import co.dds.cryptowallet.domain.model.localDatabase.WalletAddressModel
import co.dds.cryptowallet.domain.repository.WalletAddressRepository

class WalletAddressRepositoryImp(
    private val walletAddressDao: WalletAddressDao
) : WalletAddressRepository {
    override suspend fun insertWalletAddress(walletAddressModel: WalletAddressModel) =
        walletAddressDao.insertWalletAddress(walletAddressModel)

    override suspend fun getWalletAddressById(id: Int): WalletAddressModel =
        walletAddressDao.getWalletAddressById(id)

    override suspend fun deleteDatabase() = walletAddressDao.deleteDatabase()

    override suspend fun existsAddress(address: String): Boolean =
        walletAddressDao.existsAddress(address)

    override suspend fun getPrivateKeyById(id: Int): String = walletAddressDao.getPrivateKeyById(id)
}