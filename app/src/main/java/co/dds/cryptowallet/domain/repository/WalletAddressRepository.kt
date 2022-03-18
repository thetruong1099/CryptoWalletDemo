package co.dds.cryptowallet.domain.repository

import co.dds.cryptowallet.domain.model.localDatabase.WalletAddressModel

interface WalletAddressRepository {
    suspend fun insertWalletAddress(walletAddressModel: WalletAddressModel)

    suspend fun getWalletAddressById(id: Int): WalletAddressModel

    suspend fun deleteDatabase()

    suspend fun existsAddress(address: String): Boolean

    suspend fun getPrivateKeyById(id: Int): String
}