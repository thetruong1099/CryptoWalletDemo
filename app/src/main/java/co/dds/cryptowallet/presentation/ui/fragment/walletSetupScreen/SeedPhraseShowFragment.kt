package co.dds.cryptowallet.presentation.ui.fragment.walletSetupScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import co.dds.cryptowallet.R
import co.dds.cryptowallet.data.tempData.TempData
import co.dds.cryptowallet.databinding.FragmentSeedPhraseShowBinding
import co.dds.cryptowallet.presentation.ui.adapter.SeedPhraseAdapter


class SeedPhraseShowFragment : Fragment() {

    private var _binding: FragmentSeedPhraseShowBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSeedPhraseShowBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showSeedPhrase()
        navigationToConfirmSeedScreen()
    }

    private fun navigationToConfirmSeedScreen() {
        binding.btnContinue.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_seedPhraseShowFragment_to_seedPhraseConfirmFragment)
        }
    }

    private fun showSeedPhrase() {
        val seedPhraseAdapter = SeedPhraseAdapter()
        seedPhraseAdapter.setListWordSeedPhase(TempData.listSeedPhrase)
        binding.recyclerViewSeedPhrase.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = seedPhraseAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}