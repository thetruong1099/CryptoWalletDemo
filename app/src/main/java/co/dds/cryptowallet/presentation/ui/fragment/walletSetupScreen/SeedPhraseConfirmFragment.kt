package co.dds.cryptowallet.presentation.ui.fragment.walletSetupScreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import co.dds.cryptowallet.R
import co.dds.cryptowallet.data.tempData.TempData
import co.dds.cryptowallet.databinding.FragmentSeedPhraseConfirmBinding
import co.dds.cryptowallet.presentation.ui.activity.homeScreen.HomeActivity
import co.dds.cryptowallet.presentation.ui.adapter.SeedPhraseDestinationAdapter
import co.dds.cryptowallet.presentation.ui.adapter.SeedPhraseSourceAdapter
import co.dds.cryptowallet.presentation.viewmodel.SharePreferenceViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class SeedPhraseConfirmFragment : Fragment() {

    private var _binding: FragmentSeedPhraseConfirmBinding? = null
    private val binding get() = _binding!!

    private val sharePreferenceViewModel: SharePreferenceViewModel by viewModel()

    private val seedPhraseSourceAdapter by lazy {
        SeedPhraseSourceAdapter(setClickTextWordSource, requireContext())
    }

    private val seedPhraseDestinationAdapter by lazy {
        SeedPhraseDestinationAdapter(setClickTextWordDestination)
    }

    private val listSeedPhrase = mutableListOf("", "", "", "", "", "", "", "", "", "", "", "")

    private var statusFullListSeedPhrase = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSeedPhraseConfirmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSeedPhraseSourceAdapter()
        setSeedPhraseDestinationAdapter()
    }

    private fun setSeedPhraseSourceAdapter() {
        seedPhraseSourceAdapter.setListSeedPhraseSource(TempData.listSeedPhrase.shuffled())
        binding.recyclerViewSeedPhraseSource.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = seedPhraseSourceAdapter
        }
    }


    private fun setSeedPhraseDestinationAdapter() {
        binding.recyclerViewSeedPhraseDestination.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = seedPhraseDestinationAdapter
        }
    }

    private val setClickTextWordSource: (String, Boolean) -> Unit = { word, status ->
        if (status) {
            seedPhraseDestinationAdapter.addToListSeedPhraseDestination(word)
            addWordToListSeedPhrase(word)
        } else {
            seedPhraseDestinationAdapter.removeFormListSeedPhraseDestination(word)
            removeWordFromListSeedPhrase(word)
        }
        setClickCompleteBackup()
    }

    private val setClickTextWordDestination: (String) -> Unit = { word ->
        seedPhraseSourceAdapter.changItemRowIndex(word)
        removeWordFromListSeedPhrase(word)
        setClickCompleteBackup()
    }

    private fun addWordToListSeedPhrase(word: String) {
        for ((index, value) in listSeedPhrase.withIndex()) {
            if (value == "") {
                listSeedPhrase[index] = word
                statusFullListSeedPhrase = index == listSeedPhrase.size - 1
                break
            }
        }
    }

    private fun removeWordFromListSeedPhrase(word: String) {
        for ((index, value) in listSeedPhrase.withIndex()) {
            if (value == word) {
                listSeedPhrase[index] = ""
                statusFullListSeedPhrase = false
                break
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setClickCompleteBackup() {
        if (statusFullListSeedPhrase) {
            if (listSeedPhrase == TempData.listSeedPhrase) {
                binding.btnCompleteBackup.alpha = 1f
                binding.btnCompleteBackup.setOnClickListener {
                    sharePreferenceViewModel.saveMnemonic(TempData.seedPhrase)
                    val intent = Intent(requireActivity(), HomeActivity::class.java)
                    requireActivity().apply {
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                        finishAffinity()
                    }
                }
            } else {
                binding.cardViewSeedPhrase.setBackgroundResource(R.drawable.background_four)
            }
        } else {
            binding.btnCompleteBackup.alpha = 0.5f
            binding.cardViewSeedPhrase.setBackgroundResource(R.drawable.background_five)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}