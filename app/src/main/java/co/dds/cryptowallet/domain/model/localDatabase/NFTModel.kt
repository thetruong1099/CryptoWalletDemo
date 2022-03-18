package co.dds.cryptowallet.domain.model.localDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import co.dds.cryptowallet.presentation.until.Const

@Entity(tableName = Const.nftTableName)
data class NFTModel(
    @ColumnInfo(name = Const.nftNameCol) val nftName: String,
    @ColumnInfo(name = Const.tokenIDCol) val tokenId: String,
    @ColumnInfo(name = Const.contractAddressCol) val contractAddress: String,
    @ColumnInfo(name = Const.urlCol) val urlFile: String
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Const.idCol)
    var id: Int = 0
}