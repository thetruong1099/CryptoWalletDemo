package co.dds.cryptowallet.presentation.ui.activity.splashScreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.dds.cryptowallet.R
import co.dds.cryptowallet.databinding.ActivitySplashBinding
import co.dds.cryptowallet.presentation.ui.activity.walletSetupScreen.WalletSetupActivity
import co.dds.cryptowallet.presentation.ui.adapter.OnboardViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnboard()
        startToWallerSetupActivity()
    }

    private fun setOnboard(){
        val onboardAdapter = OnboardViewPagerAdapter()
        binding.viewpagerOnboard.adapter = onboardAdapter

        TabLayoutMediator(binding.tabDot, binding.viewpagerOnboard) { tab, position -> }.attach()
    }

    private fun startToWallerSetupActivity(){
        val intent = Intent(this, WalletSetupActivity::class.java)

        binding.btnGetStart.setOnClickListener {
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finishAffinity()
        }
    }
}