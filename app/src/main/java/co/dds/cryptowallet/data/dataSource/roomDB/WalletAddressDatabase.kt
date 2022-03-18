package co.dds.cryptowallet.data.dataSource.roomDB

import androidx.room.Database
import androidx.room.RoomDatabase
import co.dds.cryptowallet.domain.model.localDatabase.NFTModel
import co.dds.cryptowallet.domain.model.localDatabase.WalletAddressModel

@Database(
    entities = [
        WalletAddressModel::class,
        NFTModel::class
    ],
    version = 2,
    exportSchema = true
)

abstract class WalletAddressDatabase : RoomDatabase() {
    abstract val walletAddressDao: WalletAddressDao
    abstract val nftTokenDao: NFTTokenDao
}