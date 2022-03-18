package co.dds.cryptowallet.presentation.ui.fragment.walletSetupScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import co.dds.cryptowallet.R
import co.dds.cryptowallet.databinding.FragmentWalletSetupBinding

class WalletSetupFragment : Fragment() {
    private var _binding: FragmentWalletSetupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWalletSetupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createPassword()
        importSecretPhrase()
    }

    private fun createPassword() {
        binding.btnCreateNewWallet.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_walletSetupFragment_to_setPasswordForAppFragment)
        }
    }

    private fun importSecretPhrase() {
        binding.btnImportSecretRecoveryPhrase.setOnClickListener {
            findNavController().navigate(R.id.action_walletSetupFragment_to_importSecretPhraseFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}