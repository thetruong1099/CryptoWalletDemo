package co.dds.cryptowallet.domain.repository

import org.web3j.crypto.Credentials

interface GenerateWalletRepository {
    suspend fun generateSeedPhrase(): String

    suspend fun generateCredential(mnemonic: String, number: Int): Credentials

    suspend fun generateCheckValidateMnemonic(mnemonic: String): Boolean
}