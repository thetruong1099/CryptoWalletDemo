@file:Suppress("BlockingMethodInNonBlockingContext")

package co.dds.cryptowallet.data.repository

import android.content.Context
import co.dds.cryptowallet.domain.repository.GenerateWalletRepository
import kotlinx.coroutines.*
import org.web3j.crypto.Bip32ECKeyPair
import org.web3j.crypto.Bip32ECKeyPair.HARDENED_BIT
import org.web3j.crypto.Credentials
import org.web3j.crypto.MnemonicUtils
import org.web3j.crypto.WalletUtils
import java.io.File


private const val fileName = "cryptoWallet"

class GenerateWalletRepositoryImp(private val context: Context) : GenerateWalletRepository {
    private lateinit var file: File

    init {
        createFile()
    }

    private fun createFile() {
        CoroutineScope(Dispatchers.IO).launch {
            file = File("${context.filesDir}/$fileName")
            if (!file.mkdirs()) file.mkdirs()
            cancel()
        }
    }

    override suspend fun generateSeedPhrase(): String =
        withContext(Dispatchers.Default) {
            val wallet = WalletUtils.generateBip39Wallet("", file)
            return@withContext wallet.mnemonic
        }

    override suspend fun generateCredential(mnemonic: String, number: Int): Credentials =
        withContext(Dispatchers.Default) {
            val masterKeypair =
                Bip32ECKeyPair.generateKeyPair(
                    MnemonicUtils.generateSeed(
                        mnemonic,
                        null
                    )
                )
            val path =
                intArrayOf(44 or HARDENED_BIT, 60 or HARDENED_BIT, 0 or HARDENED_BIT, 0, number)
            val x = Bip32ECKeyPair.deriveKeyPair(masterKeypair, path)
            return@withContext Credentials.create(x)
        }

    override suspend fun generateCheckValidateMnemonic(mnemonic: String): Boolean =
        withContext(Dispatchers.Default) {
            return@withContext MnemonicUtils.validateMnemonic(mnemonic)
        }
}