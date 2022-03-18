package co.dds.cryptowallet.presentation.ui.activity.SendTokenScreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import co.dds.cryptowallet.R
import co.dds.cryptowallet.data.tempData.TempData
import co.dds.cryptowallet.databinding.ActivityConfirmTransactionBinding
import co.dds.cryptowallet.presentation.ui.activity.homeScreen.HomeActivity
import co.dds.cryptowallet.presentation.until.Const
import co.dds.cryptowallet.presentation.viewmodel.GenerateWalletViewModel
import co.dds.cryptowallet.presentation.viewmodel.TransactionTokenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.math.BigDecimal

class ConfirmTransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmTransactionBinding

    private val transactionTokenViewModel: TransactionTokenViewModel by viewModel()
    private val generateWalletViewModel: GenerateWalletViewModel by viewModel()

    private var amount = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
        setAmountETH()
        setAddressFrom()
        getGasPrice()
        setAddressTo()
        setButtonSendToken()
    }

    private fun getData() {
        amount = intent.getStringExtra(Const.amount)!!
    }

    private fun setAmountETH() {
        binding.tvAmountEth.text = amount
    }

    @SuppressLint("SetTextI18n")
    private fun setAddressFrom() {
        TempData.addressModel?.let { addressModel ->
            binding.tvAccountName.text = addressModel.accountName
            transactionTokenViewModel.getBalance(addressModel.address).observe(this) {
                binding.tvBalance.text = "$it ${getString(R.string.eth)}"
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getGasPrice() {
        transactionTokenViewModel.getGasPrice(
            TempData.addressModel!!.address,
            TempData.addressTo,
            amount
        ).observe(this) {
            binding.tvGasFee.text = "${it.toPlainString()} ${getString(R.string.eth)}"
            setTotalAmount(it)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setTotalAmount(gasPrice: BigDecimal) {
        val totalAmount = amount.toBigDecimal() + gasPrice
        binding.tvTotalAmount.text = "${totalAmount.toPlainString()} ${getString(R.string.eth)}"
    }

    private fun setButtonSendToken() {
        binding.btnSend.setOnClickListener {
            setVisibilityView()
            generateWalletViewModel.generateCredential(TempData.seedPhrase, TempData.number)
                .observe(this) { credential ->
                    transactionTokenViewModel.sendToken(credential, TempData.addressTo, amount)
                        .observe(this) {
                            showAnimationSendToken(it)
                        }
                }
        }
    }

    private fun setAddressTo() {
        binding.tvAccountNameTo.text = TempData.addressTo
    }

    private fun setVisibilityView() {
        binding.btnSend.visibility = View.GONE
        binding.animationSendToken.visibility = View.VISIBLE
    }

    private fun showAnimationSendToken(status: Boolean) {
        CoroutineScope(Dispatchers.Main).launch {
            if (status) {
                binding.animationSendToken.apply {
                    setAnimation(R.raw.correct_animation)
                    repeatCount = 0
                    playAnimation()
                }
                delay(1000)
                startActivity()
            } else {
                binding.animationSendToken.apply {
                    setAnimation(R.raw.fail_animation)
                    playAnimation()
                }
            }
        }
    }

    private fun startActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }
}