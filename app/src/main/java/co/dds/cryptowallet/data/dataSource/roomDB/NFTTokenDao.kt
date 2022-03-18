package co.dds.cryptowallet.data.dataSource.roomDB

import androidx.lifecycle.LiveData
import androidx.room.*
import co.dds.cryptowallet.domain.model.localDatabase.NFTModel

@Dao
interface NFTTokenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNftToken(nftModel: NFTModel)

    @Delete
    suspend fun removeNftToken(nftModel: NFTModel)

    @Query("select * from nft_table")
    fun getAllNftToken(): LiveData<MutableList<NFTModel>>

}