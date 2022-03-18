package co.dds.cryptowallet.domain.repository

import co.dds.cryptowallet.presentation.until.SharePreferenceLiveDataBoolean
import co.dds.cryptowallet.presentation.until.SharePreferenceLiveDataString

interface SharePreferenceRepository {

    suspend fun setPassword(passwordString: String)

    fun getPassword(): SharePreferenceLiveDataString

    suspend fun saveStatusRememberMe(status: Boolean)

    fun getStatusRememberMe(): SharePreferenceLiveDataBoolean

    suspend fun saveMnemonic(mnemonic: String)

    fun getMnemonic(): SharePreferenceLiveDataString

}