package co.dds.cryptowallet.presentation.ui.fragment.settingScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import co.dds.cryptowallet.R
import co.dds.cryptowallet.data.tempData.TempData
import co.dds.cryptowallet.databinding.FragmentShowPrivateKeyBinding
import co.dds.cryptowallet.presentation.ui.adapter.SecretRecoveryPhraseViewPagerAdapter
import co.dds.cryptowallet.presentation.ui.fragment.showScreen.ShowQRFragment
import co.dds.cryptowallet.presentation.ui.fragment.showScreen.ShowTextFragment
import co.dds.cryptowallet.presentation.viewmodel.WalletAddressRoomDBViewModel
import com.google.android.material.tabs.TabLayout
import org.koin.androidx.viewmodel.ext.android.viewModel


class ShowPrivateKeyFragment : Fragment() {

    private var _binding: FragmentShowPrivateKeyBinding? = null
    private val binding get() = _binding!!

    private val walletAddressRoomDBViewModel: WalletAddressRoomDBViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentShowPrivateKeyBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPrivateKeyByID()
        setTabLayout()
        setClickButtonCancel()
    }

    private fun getPrivateKeyByID() {
        walletAddressRoomDBViewModel.getPrivateKeyById(TempData.number + 1)
            .observe(viewLifecycleOwner) { privateKey ->
                setSecretRecoveryPhraseViewPager(privateKey)
            }
    }

    private fun setSecretRecoveryPhraseViewPager(privateKey: String) {
        val listFragment = mutableListOf(
            ShowTextFragment(privateKey, getString(R.string.your_private_key)),
            ShowQRFragment(privateKey)
        )

        val adapterViewPager = SecretRecoveryPhraseViewPagerAdapter(
            listFragment,
            childFragmentManager,
            lifecycle
        )

        binding.viewpagerPrivateKey.adapter = adapterViewPager
    }

    private fun setTabLayout() {
        binding.tabLayoutPrivateKey.apply {
            addTab(
                binding.tabLayoutPrivateKey.newTab().setText(getString(R.string.text))
            )

            addTab(
                binding.tabLayoutPrivateKey.newTab().setText(getString(R.string.qr_code))
            )

            setTabTextColors(
                ContextCompat.getColor(requireContext(), R.color.gray_1),
                ContextCompat.getColor(requireContext(), R.color.blue_1)
            )

            setSelectedTabIndicator(0)

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.let {
                        binding.viewpagerPrivateKey.currentItem = tab.position
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

            })
        }

        binding.viewpagerPrivateKey.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tabLayoutPrivateKey.selectTab(
                    binding.tabLayoutPrivateKey.getTabAt(
                        position
                    )
                )
            }
        })

    }

    private fun setClickButtonCancel() {
        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack(R.id.settingFragment, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}