package co.dds.cryptowallet.domain.model.api.nftToken

import com.google.gson.annotations.SerializedName

data class MetadataX(
    @SerializedName("nft_test1")
    val description: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("tags")
    val tags: String?,
    @SerializedName("url")
    val url: String
)