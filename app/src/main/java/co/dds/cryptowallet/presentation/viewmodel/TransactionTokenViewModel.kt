package co.dds.cryptowallet.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.dds.cryptowallet.domain.usecase.transactionTokenUseCase.*
import kotlinx.coroutines.launch
import org.web3j.crypto.Credentials
import java.math.BigDecimal

class TransactionTokenViewModel(
    private val getBalanceUseCase: GetBalanceUseCase,
    private val getGasPriceUseCase: GetGasPriceUseCase,
    private val sendTokenUseCase: SendTokenUseCase
) : ViewModel() {

    fun getBalance(address: String): LiveData<String> {
        val result = MutableLiveData<String>()
        viewModelScope.launch {
            getBalanceUseCase(address).let {
                result.postValue(it)
            }
        }
        return result
    }

    fun getGasPrice(
        walletAddress: String,
        contractAddress: String,
        value: String
    ): LiveData<BigDecimal> {
        val result = MutableLiveData<BigDecimal>()
        viewModelScope.launch {
            getGasPriceUseCase(walletAddress, contractAddress, value).let {
                result.postValue(it)
            }
        }
        return result
    }

    fun sendToken(
        credentials: Credentials,
        desAddress: String,
        amount: String
    ): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch {
            sendTokenUseCase(credentials, desAddress, amount).let {
                result.postValue(it)
            }
        }
        return result
    }
}