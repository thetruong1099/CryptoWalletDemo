package co.dds.cryptowallet.data.tempData

import co.dds.cryptowallet.domain.model.localDatabase.WalletAddressModel

object TempData {
    var seedPhrase = ""
    var listSeedPhrase = listOf<String>()
    var number = 0
    var addressModel: WalletAddressModel? = null
    var addressTo = ""
}