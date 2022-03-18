package co.dds.cryptowallet.presentation.until

import android.content.SharedPreferences
import androidx.lifecycle.LiveData

class SharePreferenceLiveDataString(
    private val sharePreference: SharedPreferences,
    private val key:String
): LiveData<String>() {
    private val preferenceChangeListener = SharedPreferences.OnSharedPreferenceChangeListener{
            sharedPreferences, key ->

        if(this.key==key){
            value = sharedPreferences?.getString(key,"")
        }
    }

    override fun onActive() {
        super.onActive()
        value = sharePreference.getString(key,"")
        sharePreference.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    override fun onInactive() {
        super.onInactive()
        sharePreference.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
    }
}