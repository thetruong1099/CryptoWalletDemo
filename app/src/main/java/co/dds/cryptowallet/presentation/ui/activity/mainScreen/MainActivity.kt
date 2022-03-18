package co.dds.cryptowallet.presentation.ui.activity.mainScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.dds.cryptowallet.data.tempData.TempData
import co.dds.cryptowallet.databinding.ActivityMainBinding
import co.dds.cryptowallet.presentation.ui.activity.homeScreen.HomeActivity
import co.dds.cryptowallet.presentation.ui.activity.loginScreen.LoginActivity
import co.dds.cryptowallet.presentation.ui.activity.splashScreen.SplashActivity
import co.dds.cryptowallet.presentation.viewmodel.SharePreferenceViewModel
import co.dds.cryptowallet.presentation.viewmodel.WalletAddressRoomDBViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val sharePreferenceViewModel: SharePreferenceViewModel by viewModel()
    private val walletAddressRoomDBViewModel: WalletAddressRoomDBViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startToActivity()
    }

    private fun startToActivity() {
        sharePreferenceViewModel.getMnemonic().observe(this) { mnemonic ->
            if (mnemonic.isNotEmpty()) {
                sharePreferenceViewModel.getStatusLogin().observe(this) { status ->
                    if (status) {
                        startToHomeActivity()
                    } else {
                        startToLoginActivity()
                    }
                }
            } else {
                walletAddressRoomDBViewModel.deleteDatabase()
                startToSplashActivity()
            }
        }
    }

    private fun startToSplashActivity() {
        val intent = Intent(this, SplashActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    private fun startToHomeActivity() {
        sharePreferenceViewModel.getMnemonic().observe(this) { mnemonic ->
            TempData.seedPhrase = mnemonic
            TempData.number = 0

            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }

    private fun startToLoginActivity() {
        sharePreferenceViewModel.getMnemonic().observe(this) { mnemonic ->
            TempData.seedPhrase = mnemonic
            TempData.number = 0

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }

}