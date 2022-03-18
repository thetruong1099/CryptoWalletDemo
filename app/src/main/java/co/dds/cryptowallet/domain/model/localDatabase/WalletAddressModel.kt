package co.dds.cryptowallet.domain.model.localDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import co.dds.cryptowallet.presentation.until.Const

@Entity(tableName = Const.tableName)
data class WalletAddressModel(
    @ColumnInfo(name = Const.accountNameCol) val accountName: String,
    @ColumnInfo(name = Const.addressCol) val address: String,
    @ColumnInfo(name = Const.privateKeyCol) val privateKey: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Const.idCol)
    var id: Int = 0
}