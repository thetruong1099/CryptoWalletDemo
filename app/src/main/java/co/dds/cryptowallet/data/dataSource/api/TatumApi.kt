package co.dds.cryptowallet.data.dataSource.api

import co.dds.cryptowallet.domain.model.api.nftToken.MetadataX
import co.dds.cryptowallet.domain.model.api.nftToken.NFTTokenModel
import co.dds.cryptowallet.domain.model.api.nftTokenMetadata.NFTTokenMetadataModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TatumApi {

    @GET("/v3/nft/metadata/{chain}/{contractAddress}/{token}")
    suspend fun getNFTTokenMetadata(
        @Path("chain") chain: String,
        @Path("contractAddress") contractAddress: String,
        @Path("token") token: String,
        @Query("account") account: String
    ): NFTTokenMetadataModel

    @GET("/v3/nft/address/balance/{chain}/{address}")
    suspend fun getNFTTokenForAddress(
        @Path("chain") chain: String,
        @Path("address") address: String
    ): NFTTokenModel

    @GET("/v3/ipfs/{id}")
    suspend fun getNFTFileFromIPFS(
        @Path("id") id: String
    ): MetadataX
}