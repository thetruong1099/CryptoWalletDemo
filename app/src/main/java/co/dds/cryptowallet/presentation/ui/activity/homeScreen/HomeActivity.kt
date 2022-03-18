package co.dds.cryptowallet.presentation.ui.activity.homeScreen

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import co.dds.cryptowallet.R
import co.dds.cryptowallet.data.tempData.TempData
import co.dds.cryptowallet.databinding.ActivityHomeBinding
import co.dds.cryptowallet.databinding.LayoutHeaderBinding
import co.dds.cryptowallet.presentation.ui.fragment.dialogFragment.ShowAddressBottomSheetDialogFragment
import co.dds.cryptowallet.presentation.viewmodel.TransactionTokenViewModel
import co.dds.cryptowallet.presentation.viewmodel.WalletAddressRoomDBViewModel
import com.google.android.material.navigation.NavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var bindingMainActivity: ActivityHomeBinding
    private lateinit var bindingNavHeader: LayoutHeaderBinding

    private lateinit var navHost: NavHostFragment
    private lateinit var navController: NavController

    private val walletAddressRoomDBViewModel: WalletAddressRoomDBViewModel by viewModel()
    private val transactionTokenViewModel: TransactionTokenViewModel by viewModel()

    private val dialog by lazy { ShowAddressBottomSheetDialogFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMainActivity = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(bindingMainActivity.root)

        bindingNavHeader = LayoutHeaderBinding.inflate(layoutInflater)
        bindingMainActivity.navDrawView.addHeaderView(bindingNavHeader.root)

        navHost =
            supportFragmentManager.findFragmentById(R.id.main_wallet_fragment_container_view) as NavHostFragment
        navController = navHost.navController

        navigationDrawer()
        setInformationWalletAccount()
        listenerButtonAddFunds()
    }

    private fun navigationDrawer() {
        bindingMainActivity.navDrawView.bringToFront()
        val toggle =
            ActionBarDrawerToggle(
                this,
                bindingMainActivity.drawerLayout,
                bindingMainActivity.toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )
        bindingMainActivity.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        bindingMainActivity.navDrawView.setNavigationItemSelectedListener(this)
    }

    override fun onStart() {
        super.onStart()
        bindingMainActivity.navDrawView.setCheckedItem(R.id.nav_wallet)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_wallet -> {
                navController.navigate(R.id.walletHomeFragment)
            }

//            R.id.nav_transaction_history -> {
//
//            }

            R.id.nav_setting -> {
                navController.navigate(R.id.settingFragment)
            }

//            R.id.nav_log_out -> {
//
//            }
        }

        bindingMainActivity.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setInformationWalletAccount() {
        walletAddressRoomDBViewModel.getWalletAddressById(TempData.number + 1)
            .observe(this) {
                it.accountName.let { name ->
                    bindingNavHeader.tvAccountName.text = name
                }
                it.address.let { address ->
                    bindingNavHeader.tvAddress.text = address
                    getBalance(address)
                }
                TempData.addressModel = it
            }
    }

    @SuppressLint("SetTextI18n")
    private fun getBalance(address: String) {
        transactionTokenViewModel.getBalance(address).observe(this) {
            bindingNavHeader.tvBalance.text = "$it ${getString(R.string.eth)}"
        }
    }

    private fun listenerButtonAddFunds() {
        bindingNavHeader.btnAddFunds.setOnClickListener {
            dialog.show(supportFragmentManager, "CusTom Dialog")
        }
    }
}