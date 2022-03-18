package co.dds.cryptowallet.domain.usecase.nftUseCase

import co.dds.cryptowallet.domain.repository.TatumRepository

class GetNftMetadataUseCase(
    private val tatumRepository: TatumRepository
) {
    suspend operator fun invoke(
        chain: String,
        contractAddress: String,
        token: String,
        account: String
    ) = tatumRepository.getNFTTokenMetadata(chain, contractAddress, token, account)
}