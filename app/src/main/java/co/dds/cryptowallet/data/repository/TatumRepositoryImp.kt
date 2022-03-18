package co.dds.cryptowallet.data.repository

import co.dds.cryptowallet.domain.model.api.nftToken.MetadataX
import co.dds.cryptowallet.domain.model.api.nftToken.NFTTokenModel
import co.dds.cryptowallet.domain.model.api.nftTokenMetadata.NFTTokenMetadataModel
import co.dds.cryptowallet.data.dataSource.api.TatumApi
import co.dds.cryptowallet.domain.repository.TatumRepository
import co.dds.cryptowallet.presentation.until.Resource
import co.dds.cryptowallet.presentation.until.ResponseHandler

class TatumRepositoryImp(
    private val tatumApi: TatumApi,
    private val responseHandler: ResponseHandler
) : TatumRepository {

    override suspend fun getNFTTokenMetadata(
        chain: String,
        contractAddress: String,
        token: String,
        account: String
    ): Resource<NFTTokenMetadataModel> {
        return try {
            responseHandler.handlerSuccess(
                tatumApi.getNFTTokenMetadata(
                    chain,
                    contractAddress,
                    token,
                    account
                )
            )
        } catch (e: Exception) {
            responseHandler.handlerException(e)
        }
    }

    override suspend fun getNFTTokenForAddress(
        chain: String,
        address: String
    ): Resource<NFTTokenModel> {
        return try {
            responseHandler.handlerSuccess(
                tatumApi.getNFTTokenForAddress(chain, address)
            )
        } catch (e: Exception) {
            responseHandler.handlerException(e)
        }
    }

    override suspend fun getNFTFileFromIPFS(id: String): Resource<MetadataX> {
        return try {
            responseHandler.handlerSuccess(
                tatumApi.getNFTFileFromIPFS(id)
            )
        } catch (e: Exception) {
            responseHandler.handlerException(e)
        }
    }
}