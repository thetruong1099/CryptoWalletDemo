package co.dds.cryptowallet.presentation.until

import java.math.BigInteger
import java.security.MessageDigest

private val sha256 = "SHA-256"

fun String.sha256(): String {
    val md = MessageDigest.getInstance(sha256)
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}

fun ByteArray.toHex(): String {
    val hexArray = "0123456789abcdef".toCharArray()
    val hexChars = CharArray(this.size * 2)
    for (j in this.indices) {
        val v = this[j].toInt() and 0xFF

        hexChars[j * 2] = hexArray[v ushr 4]
        hexChars[j * 2 + 1] = hexArray[v and 0x0F]
    }
    return String(hexChars)
}