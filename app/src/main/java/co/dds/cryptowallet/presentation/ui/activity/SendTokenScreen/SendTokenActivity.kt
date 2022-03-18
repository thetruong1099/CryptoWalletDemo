package co.dds.cryptowallet.presentation.ui.activity.SendTokenScreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import co.dds.cryptowallet.R
import co.dds.cryptowallet.data.tempData.TempData
import co.dds.cryptowallet.databinding.ActivitySendTokenBinding
import co.dds.cryptowallet.presentation.ui.activity.qrCode.QRCodeScannerActivity
import co.dds.cryptowallet.presentation.viewmodel.TempDataViewModel
import co.dds.cryptowallet.presentation.viewmodel.TransactionTokenViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SendTokenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySendTokenBinding

    private val transactionTokenViewModel: TransactionTokenViewModel by viewModel()

    private val tempDataViewModel: TempDataViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendTokenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTextviewAddressFrom()
        startScanner()
        listenerButtonNext()
    }

    override fun onResume() {
        super.onResume()
        getCodeFromQRCodeScan()
    }

    private fun startScanner() {
        binding.btnScan.setOnClickListener {
            val intent = Intent(this, QRCodeScannerActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getCodeFromQRCodeScan() {
        tempDataViewModel.getCodeLiveData().observe(this) {
            TempData.addressTo = it
            if (it.isNotEmpty()) {
                setTextViewAddressTo(it)
            }
        }
    }

    private fun setTextViewAddressTo(addressTo: String) {
        binding.cardViewAvatarTo.visibility = View.VISIBLE
        binding.tvAccountNameTo.visibility = View.VISIBLE
        binding.btnNext.visibility = View.VISIBLE
        binding.tvAccountNameTo.text = addressTo
    }

    private fun listenerButtonNext() {
        binding.btnNext.setOnClickListener {
            val intent = Intent(this, SetAmountActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setTextviewAddressFrom() {
        TempData.addressModel?.let { addressModel ->
            binding.tvAccountName.text = addressModel.accountName
            transactionTokenViewModel.getBalance(addressModel.address).observe(this) {
                binding.tvBalance.text = "$it ${getString(R.string.eth)}"
            }
        }
    }
}