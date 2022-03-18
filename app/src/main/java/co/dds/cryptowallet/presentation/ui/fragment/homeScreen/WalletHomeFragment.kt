package co.dds.cryptowallet.presentation.ui.fragment.homeScreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import co.dds.cryptowallet.R
import co.dds.cryptowallet.data.tempData.TempData
import co.dds.cryptowallet.databinding.FragmentWalletHomeBinding
import co.dds.cryptowallet.presentation.ui.activity.SendTokenScreen.SendTokenActivity
import co.dds.cryptowallet.presentation.ui.adapter.SecretRecoveryPhraseViewPagerAdapter
import co.dds.cryptowallet.presentation.ui.fragment.dialogFragment.ShowAddressBottomSheetDialogFragment
import co.dds.cryptowallet.presentation.ui.fragment.showScreen.ShowListNftFragment
import co.dds.cryptowallet.presentation.ui.fragment.showScreen.ShowListTokenFragment
import co.dds.cryptowallet.presentation.viewmodel.TransactionTokenViewModel
import co.dds.cryptowallet.presentation.viewmodel.WalletAddressRoomDBViewModel
import com.google.android.material.tabs.TabLayout
import org.koin.androidx.viewmodel.ext.android.viewModel


class WalletHomeFragment : Fragment() {
    private var _binding: FragmentWalletHomeBinding? = null
    private val binding get() = _binding!!

    private val walletAddressRoomDBViewModel: WalletAddressRoomDBViewModel by viewModel()
    private val transactionTokenViewModel: TransactionTokenViewModel by viewModel()

    private val dialog by lazy { ShowAddressBottomSheetDialogFragment() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentWalletHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showPublicAddress()
        listenerButtonReceiver()
        listenerButtonSend()
        setTabLayout()
        setTokenNftViewpager()
    }

    private fun showPublicAddress() {
        walletAddressRoomDBViewModel.getWalletAddressById(TempData.number + 1)
            .observe(viewLifecycleOwner) {
                binding.tvAccountName.text = it.accountName
                binding.tvAddress.text = it.address

                binding.tvAccountName.text = it.accountName
                binding.tvAddress.text = it.address

                TempData.addressModel = it

                getBalance(it.address)
            }
    }

    @SuppressLint("SetTextI18n")
    private fun getBalance(address: String) {
        transactionTokenViewModel.getBalance(address).observe(viewLifecycleOwner) {
            binding.tvBalance.text = "$it ${getString(R.string.eth)}"
        }
    }

    private fun listenerButtonSend() {
        binding.btnSend.setOnClickListener {
            val intent = Intent(requireContext(), SendTokenActivity::class.java)
            startActivity(intent)
        }
    }

    private fun listenerButtonReceiver() {
        binding.btnReceive.setOnClickListener {
            dialog.show(childFragmentManager, "CusTom Dialog")
        }
    }

    private fun setTabLayout() {
        binding.tabLayoutTokenNft.apply {
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.let {
                        binding.viewpagerTokenNft.currentItem = tab.position
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

            })
        }

        binding.viewpagerTokenNft.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayoutTokenNft.selectTab(
                    binding.tabLayoutTokenNft.getTabAt(position)
                )
            }
        })
    }

    private fun setTokenNftViewpager() {
        val listFragment = mutableListOf(
            ShowListTokenFragment(),
            ShowListNftFragment()
        )

        val adapterViewPager = SecretRecoveryPhraseViewPagerAdapter(
            listFragment,
            childFragmentManager,
            lifecycle
        )

        binding.viewpagerTokenNft.adapter = adapterViewPager
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}