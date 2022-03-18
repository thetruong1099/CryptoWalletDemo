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
import co.dds.cryptowallet.databinding.FragmentShowSecretRecoveryPhraseBinding
import co.dds.cryptowallet.presentation.ui.adapter.SecretRecoveryPhraseViewPagerAdapter
import co.dds.cryptowallet.presentation.ui.fragment.showScreen.ShowQRFragment
import co.dds.cryptowallet.presentation.ui.fragment.showScreen.ShowTextFragment
import com.google.android.material.tabs.TabLayout


class ShowSecretRecoveryPhraseFragment : Fragment() {

    private var _binding: FragmentShowSecretRecoveryPhraseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentShowSecretRecoveryPhraseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPager()
        setTabLayout()
        setClickButtonCancel()
    }

    private fun setViewPager() {
        val listFragment = mutableListOf(
            ShowTextFragment(TempData.seedPhrase, getString(R.string.your_secret_recovery_phrase)),
            ShowQRFragment(TempData.seedPhrase)
        )

        val adapterViewPager = SecretRecoveryPhraseViewPagerAdapter(
            listFragment,
            childFragmentManager,
            lifecycle
        )

        binding.viewpagerShowSecret.adapter = adapterViewPager
    }

    private fun setTabLayout() {
        binding.tabLayoutSecretRecoveryPhrase.apply {
            addTab(
                binding.tabLayoutSecretRecoveryPhrase.newTab().setText(getString(R.string.text))
            )

            addTab(
                binding.tabLayoutSecretRecoveryPhrase.newTab().setText(getString(R.string.qr_code))
            )

            setTabTextColors(
                ContextCompat.getColor(requireContext(), R.color.gray_1),
                ContextCompat.getColor(requireContext(), R.color.blue_1)
            )

            setSelectedTabIndicator(0)

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.let {
                        binding.viewpagerShowSecret.currentItem = tab.position
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

            })
        }

        binding.viewpagerShowSecret.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tabLayoutSecretRecoveryPhrase.selectTab(
                    binding.tabLayoutSecretRecoveryPhrase.getTabAt(
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