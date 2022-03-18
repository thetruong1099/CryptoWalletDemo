package co.dds.cryptowallet.data.repository

import co.dds.cryptowallet.domain.repository.TransactionTokenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.tx.Transfer
import org.web3j.utils.Convert
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.concurrent.TimeUnit

class TransactionTokenRepositoryImp(private val web3j: Web3j) : TransactionTokenRepository {

    override suspend fun getBalance(address: String): String = withContext(Dispatchers.IO) {
        val balanceBigInteger = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST)
            .sendAsync().get(10, TimeUnit.SECONDS).balance

        val balanceETH = Convert.fromWei(balanceBigInteger.toString(), Convert.Unit.ETHER)
            .setScale(6, RoundingMode.HALF_EVEN)

        return@withContext balanceETH.toPlainString()
    }

    override suspend fun sendTokenEth(
        credentials: Credentials,
        address: String,
        amount: String
    ): Boolean =
        withContext(Dispatchers.IO) {
            return@withContext Transfer.sendFunds(
                web3j,
                credentials,
                address,
                BigDecimal(amount),
                Convert.Unit.ETHER
            ).send().isStatusOK
        }

    override suspend fun getTransactionHistory(address: String) {
        val listTransaction = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true)
            .send().block.transactions
    }

    override suspend fun getGasPrice(
        walletAddress: String,
        contractAddress: String,
        value: String
    ): BigDecimal = withContext(Dispatchers.IO) {

        val gasPriceWEI = web3j.ethGasPrice().send().gasPrice

        val gasPriceGWEI = Convert.fromWei(gasPriceWEI.toString(), Convert.Unit.GWEI)

        val valueInteger = Convert.toWei(value, Convert.Unit.ETHER)
            .toBigInteger()

        val estimateGasGWEI = web3j.ethEstimateGas(
            Transaction.createEtherTransaction(
                walletAddress,
                null,
                null,
                null,
                contractAddress,
                valueInteger
            )
        ).send().amountUsed

        val gasCostGWEI = estimateGasGWEI * gasPriceGWEI.toBigInteger()

        val gasCostWEI = Convert.toWei(gasCostGWEI.toString(), Convert.Unit.GWEI)

        return@withContext Convert.fromWei(gasCostWEI, Convert.Unit.ETHER)
    }


}