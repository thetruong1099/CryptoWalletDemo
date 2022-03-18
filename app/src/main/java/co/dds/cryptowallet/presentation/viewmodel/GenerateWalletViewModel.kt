package co.dds.cryptowallet.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.dds.cryptowallet.domain.usecase.generateWalletUseCase.GenerateCheckValidateMnemonicUseCase
import co.dds.cryptowallet.domain.usecase.generateWalletUseCase.GenerateCredentialUseCase
import co.dds.cryptowallet.domain.usecase.generateWalletUseCase.GenerateSeedPhraseUseCase
import kotlinx.coroutines.launch
import org.web3j.crypto.Credentials

class GenerateWalletViewModel(
    private val generateSeedPhraseUseCase: GenerateSeedPhraseUseCase,
    private val generateCredentialUseCase: GenerateCredentialUseCase,
    private val generateCheckValidateMnemonicUseCase: GenerateCheckValidateMnemonicUseCase
) : ViewModel() {

    fun generateSeedPhrase(): LiveData<String> {
        val result = MutableLiveData<String>()
        viewModelScope.launch {
            generateSeedPhraseUseCase().let {
                result.postValue(it)
            }

        }
        return result
    }

    fun generateCredential(mnemonic: String, number: Int): LiveData<Credentials> {
        val result = MutableLiveData<Credentials>()
        viewModelScope.launch {
            generateCredentialUseCase(mnemonic, number).let {
                result.postValue(it)
            }
        }
        return result
    }

    fun checkValidateMnemonic(mnemonic: String): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch {
            generateCheckValidateMnemonicUseCase(mnemonic).let {
                result.postValue(it)
            }
        }
        return result
    }
}