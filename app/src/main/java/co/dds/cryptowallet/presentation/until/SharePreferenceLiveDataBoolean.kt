package co.dds.cryptowallet.presentation.until

import android.content.SharedPreferences
import androidx.lifecycle.LiveData

class SharePreferenceLiveDataBoolean(
    private val sharePreference: SharedPreferences,
    private val key: String
) : LiveData<Boolean>() {
    private val preferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->

            if (this.key == key) {
                value = sharedPreferences?.getBoolean(key, false)
            }
        }

    override fun onActive() {
        super.onActive()
        value = sharePreference.getBoolean(key, false)
        sharePreference.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    override fun onInactive() {
        super.onInactive()
        sharePreference.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
    }
}