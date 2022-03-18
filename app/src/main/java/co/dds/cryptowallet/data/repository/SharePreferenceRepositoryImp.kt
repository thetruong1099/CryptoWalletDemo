package co.dds.cryptowallet.data.repository

import android.content.Context
import android.content.SharedPreferences
import co.dds.cryptowallet.domain.repository.SharePreferenceRepository
import co.dds.cryptowallet.presentation.until.SharePreferenceLiveDataBoolean
import co.dds.cryptowallet.presentation.until.SharePreferenceLiveDataString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val sharePreferenceDataName = "myData"
private const val passwordKey = "myPassword"
private const val statusRememberMeKey = "saveLogin"
private const val mnemonicKey = "mnemonic"

class SharePreferenceRepositoryImp(context: Context) : SharePreferenceRepository {

    private val sharedPreferences =
        context.getSharedPreferences(sharePreferenceDataName, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    override suspend fun setPassword(passwordString: String) {
        withContext(Dispatchers.IO) {
            editor.putString(passwordKey, passwordString)
            editor.commit()
        }
    }

    override fun getPassword() = SharePreferenceLiveDataString(sharedPreferences, passwordKey)

    override suspend fun saveStatusRememberMe(status: Boolean) {
        withContext(Dispatchers.IO) {
            editor.putBoolean(statusRememberMeKey, status)
            editor.commit()
        }
    }

    override fun getStatusRememberMe(): SharePreferenceLiveDataBoolean =
        SharePreferenceLiveDataBoolean(sharedPreferences, statusRememberMeKey)

    override suspend fun saveMnemonic(mnemonic: String) {
        withContext(Dispatchers.IO) {
            editor.putString(mnemonicKey, mnemonic)
            editor.commit()
        }
    }

    override fun getMnemonic(): SharePreferenceLiveDataString =
        SharePreferenceLiveDataString(sharedPreferences, mnemonicKey)
}