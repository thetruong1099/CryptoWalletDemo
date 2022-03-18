package co.dds.cryptowallet.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.dds.cryptowallet.domain.model.localDatabase.WalletAddressModel
import co.dds.cryptowallet.domain.usecase.walletAddressRoomDBUseCase.*
import kotlinx.coroutines.launch

class WalletAddressRoomDBViewModel(
    private val insertWalletAddressUseCase: InsertWalletAddressUseCase,
    private val getWalletAddressByIdUseCase: GetWalletAddressByIdUseCase,
    private val deleteDatabaseUseCase: DeleteDatabaseUseCase,
    private val existsAddressUseCase: ExistsAddressUseCase,
    private val getPrivateKeyByIdUseCase: GetPrivateKeyByIdUseCase
) : ViewModel() {

    fun insertWalletAddress(walletAddressModel: WalletAddressModel) = viewModelScope.launch {
        insertWalletAddressUseCase(walletAddressModel)
    }

    fun getWalletAddressById(id: Int): MutableLiveData<WalletAddressModel> {
        val result = MutableLiveData<WalletAddressModel>()
        viewModelScope.launch {
            getWalletAddressByIdUseCase(id).let {
                result.postValue(it)
            }
        }
        return result
    }

    fun checkAddressExists(address: String): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch {
            existsAddressUseCase(address).let {
                result.postValue(it)
            }
        }
        return result
    }

    fun getPrivateKeyById(id: Int): LiveData<String> {
        val result = MutableLiveData<String>()
        viewModelScope.launch {
            getPrivateKeyByIdUseCase(id).let {
                result.postValue(it)
            }
        }
        return result
    }

    fun deleteDatabase() {
        viewModelScope.launch {
            deleteDatabaseUseCase()
        }
    }
}