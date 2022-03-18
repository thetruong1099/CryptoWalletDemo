package co.dds.cryptowallet.domain.model.api.nftToken

import com.google.gson.annotations.SerializedName

data class Metadata(
    @SerializedName("metadata")
    val metadata: MetadataX,
    @SerializedName("tokenId")
    val tokenId: String,
    @SerializedName("url")
    val url: String
)