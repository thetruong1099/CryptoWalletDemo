package co.dds.cryptowallet.presentation.viewmodel

import androidx.lifecycle.*
import co.dds.cryptowallet.domain.model.localDatabase.NFTModel
import co.dds.cryptowallet.domain.usecase.nftTokenRoomDbUseCase.DeleteNftTokenUseCase
import co.dds.cryptowallet.domain.usecase.nftTokenRoomDbUseCase.GetAllNftTokenUseCase
import co.dds.cryptowallet.domain.usecase.nftTokenRoomDbUseCase.InsertNftTokenUseCase
import co.dds.cryptowallet.domain.usecase.nftUseCase.GetNftFileFromIPFSUseCase
import co.dds.cryptowallet.domain.usecase.nftUseCase.GetNftMetadataUseCase
import co.dds.cryptowallet.presentation.until.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NFTViewModel(
    private val getNftMetadataUseCase: GetNftMetadataUseCase,
    private val getNftFileFromIPFSUseCase: GetNftFileFromIPFSUseCase,
    private val getAllNftTokenUseCase: GetAllNftTokenUseCase,
    private val insertNftTokenUseCase: InsertNftTokenUseCase,
    private val deleteNftTokenUseCase: DeleteNftTokenUseCase
) : ViewModel() {

    fun getNftMetadata(
        chain: String,
        contractAddress: String,
        token: String,
        account: String
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        emit(getNftMetadataUseCase.invoke(chain, contractAddress, token, account))
    }

    fun getNftFileFromIPFS(id: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        emit(getNftFileFromIPFSUseCase.invoke(id))
    }

    fun insertNftToken(nftModel: NFTModel) =
        viewModelScope.launch(Dispatchers.IO) { insertNftTokenUseCase(nftModel) }

    fun deleteNftToken(nftModel: NFTModel) =
        viewModelScope.launch(Dispatchers.IO) { deleteNftTokenUseCase(nftModel) }

    fun getAllNftToken(): LiveData<MutableList<NFTModel>> = getAllNftTokenUseCase.invoke()
}