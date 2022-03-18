package co.dds.cryptowallet.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.dds.cryptowallet.domain.usecase.sharePreferenceUseCase.*
import kotlinx.coroutines.launch

class SharePreferenceViewModel(
    private val setPassWordUseCase: SetPassWordUseCase,
    private val getPasswordUseCase: GetPasswordUseCase,
    private val saveLoginUseCase: SaveLoginUseCase,
    private val getStatusLoginSave: GetStatusLoginSave,
    private val saveMnemonicUseCase: SaveMnemonicUseCase,
    private val getMnemonicUseCase: GetMnemonicUseCase
) : ViewModel() {

    fun setPassword(password: String) = viewModelScope.launch {
        setPassWordUseCase(password)
    }

    fun getPassword() = getPasswordUseCase.invoke()

    fun saveStatusLogin(status: Boolean) = viewModelScope.launch {
        saveLoginUseCase(status)
    }

    fun getStatusLogin() = getStatusLoginSave.invoke()

    fun saveMnemonic(mnemonic: String) = viewModelScope.launch {
        saveMnemonicUseCase(mnemonic)
    }

    fun getMnemonic() = getMnemonicUseCase.invoke()
}