package co.dds.cryptowallet.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TempDataViewModel() : ViewModel() {
    private val codeLiveData = MutableLiveData<String>()

    fun setCodeLiveData(code: String) {
        codeLiveData.value = code
    }

    fun getCodeLiveData(): MutableLiveData<String> = codeLiveData
}