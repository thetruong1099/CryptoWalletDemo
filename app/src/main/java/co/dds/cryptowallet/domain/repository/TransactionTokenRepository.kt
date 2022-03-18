package co.dds.cryptowallet.domain.repository

import org.web3j.crypto.Credentials
import java.math.BigDecimal
import java.math.BigInteger

interface TransactionTokenRepository {
    suspend fun getBalance(address: String): String

    suspend fun sendTokenEth(credentials: Credentials, address: String, amount: String): Boolean

    suspend fun getTransactionHistory(address: String)

    suspend fun getGasPrice(
        walletAddress: String,
        contractAddress: String,
        value: String
    ): BigDecimal
}