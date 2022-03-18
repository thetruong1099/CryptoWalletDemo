package co.dds.cryptowallet.presentation.ui.activity.loginScreen

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import co.dds.cryptowallet.databinding.ActivityLoginBinding
import co.dds.cryptowallet.presentation.ui.activity.homeScreen.HomeActivity
import co.dds.cryptowallet.presentation.until.sha256
import co.dds.cryptowallet.presentation.viewmodel.SharePreferenceViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val sharePreferenceViewModel: SharePreferenceViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setClickButtonLogIn()
        listenerEditText()
    }

    private fun setClickButtonLogIn() {
        binding.btnLogIn.setOnClickListener {
            val passwordInput = binding.edtPassword.text.trim().toString().sha256()
            sharePreferenceViewModel.getPassword().observe(this) { password ->
                if (passwordInput == password) {
                    sharePreferenceViewModel.saveStatusLogin(binding.switchRememberMe.isChecked)
                    startToHomeActivity()
                }
            }
        }
    }

    private fun startToHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager!!.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun listenerEditText() {
        binding.edtPassword.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) hideKeyboard(v)
        }
    }
}