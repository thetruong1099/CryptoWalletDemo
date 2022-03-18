package co.dds.cryptowallet.domain.model.api.nftTokenMetadata

import com.google.gson.annotations.SerializedName

data class NFTTokenMetadataModel(
    @SerializedName("data")
    val data: String
)