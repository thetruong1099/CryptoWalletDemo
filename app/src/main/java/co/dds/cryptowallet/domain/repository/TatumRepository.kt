package co.dds.cryptowallet.domain.repository

import co.dds.cryptowallet.domain.model.api.nftToken.MetadataX
import co.dds.cryptowallet.domain.model.api.nftToken.NFTTokenModel
import co.dds.cryptowallet.domain.model.api.nftTokenMetadata.NFTTokenMetadataModel
import co.dds.cryptowallet.presentation.until.Resource

interface TatumRepository {
    suspend fun getNFTTokenMetadata(
        chain: String,
        contractAddress: String,
        token: String,
        account: String
    ): Resource<NFTTokenMetadataModel>

    suspend fun getNFTTokenForAddress(
        chain: String,
        account: String
    ): Resource<NFTTokenModel>

    suspend fun getNFTFileFromIPFS(
        id: String
    ): Resource<MetadataX>
}