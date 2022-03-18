package co.dds.cryptowallet.presentation.ui.activity.SendTokenScreen

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import co.dds.cryptowallet.R
import co.dds.cryptowallet.data.tempData.TempData
import co.dds.cryptowallet.databinding.ActivitySetAmountBinding
import co.dds.cryptowallet.presentation.until.Const
import co.dds.cryptowallet.presentation.viewmodel.TransactionTokenViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SetAmountActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySetAmountBinding

    private val transactionTokenViewModel: TransactionTokenViewModel by viewModel()

    private var balance = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetAmountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getBalance()
        listenerButtonNext()
        listenerEditText()
    }

    @SuppressLint("SetTextI18n")
    private fun getBalance() {
        TempData.addressModel?.let { addressModel ->
            transactionTokenViewModel.getBalance(addressModel.address).observe(this) {
                binding.tvBalance.text = "$it ${getString(R.string.eth)}"
                balance = it.toDouble()
            }
        }
    }

    private fun listenerButtonNext() {
        binding.btnNext.setOnClickListener {
            val amount = binding.edtAmount.text.trim().toString()

            if (amount.isNotEmpty()) {
                transactionTokenViewModel.getGasPrice(
                    TempData.addressModel!!.address,
                    TempData.addressTo,
                    amount
                ).observe(this) { gasPrice ->
                    if (amount.toBigDecimal() + gasPrice < balance.toBigDecimal()) {
                        val intent = Intent(this, ConfirmTransactionActivity::class.java).apply {
                            putExtra(Const.amount, amount)
                        }
                        startActivity(intent)
                    } else {
                        showDialog(getString(R.string.insufficient_balance))
                    }
                }
            } else {
                showDialog(getString(R.string.amount_has_not_been_set))
            }
        }
    }

    private fun showDialog(notificationMessage: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(notificationMessage)
        builder.setPositiveButton(getString(R.string.yes)) { dialogInterface, which ->
            dialogInterface.cancel()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager!!.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun listenerEditText() {
        binding.edtAmount.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) hideKeyboard(v)
        }
    }
}