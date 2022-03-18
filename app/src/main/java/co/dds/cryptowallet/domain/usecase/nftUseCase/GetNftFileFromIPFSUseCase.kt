package co.dds.cryptowallet.domain.usecase.nftUseCase

import co.dds.cryptowallet.domain.repository.TatumRepository

class GetNftFileFromIPFSUseCase(
    private val tatumRepository: TatumRepository
) {
    suspend operator fun invoke(id: String) = tatumRepository.getNFTFileFromIPFS(id)
}