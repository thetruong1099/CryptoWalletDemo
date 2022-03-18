package co.dds.cryptowallet.data.dataSource.roomDB

import androidx.room.*
import co.dds.cryptowallet.domain.model.localDatabase.WalletAddressModel

@Dao
interface WalletAddressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWalletAddress(walletAddressModel: WalletAddressModel)

    @Query("select * from wallet_address_table where id_col=:id")
    suspend fun getWalletAddressById(id: Int): WalletAddressModel

    @Query("delete from wallet_address_table")
    suspend fun deleteDatabase()

    @Query("select exists (select 1 from wallet_address_table where address_col=:address)")
    suspend fun existsAddress(address: String): Boolean

    @Query("select private_key_col from wallet_address_table where id_col=:id")
    suspend fun getPrivateKeyById(id: Int): String
}