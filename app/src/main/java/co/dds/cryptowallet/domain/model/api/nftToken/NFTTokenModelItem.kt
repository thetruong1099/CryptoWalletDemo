package co.dds.cryptowallet.domain.model.api.nftToken

import com.google.gson.annotations.SerializedName

data class NFTTokenModelItem(
    @SerializedName("balances")
    val balances: List<String>,
    @SerializedName("contractAddress")
    val contractAddress: String,
    @SerializedName("metadata")
    val metadata: List<Metadata>
)