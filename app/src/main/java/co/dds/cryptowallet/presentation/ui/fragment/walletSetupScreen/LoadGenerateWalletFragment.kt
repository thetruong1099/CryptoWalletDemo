package co.dds.cryptowallet.presentation.ui.fragment.walletSetupScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import co.dds.cryptowallet.R
import co.dds.cryptowallet.data.tempData.TempData
import co.dds.cryptowallet.databinding.FragmentLoadGenerateWalletBinding
import co.dds.cryptowallet.domain.model.localDatabase.WalletAddressModel
import co.dds.cryptowallet.presentation.until.Const
import co.dds.cryptowallet.presentation.viewmodel.GenerateWalletViewModel
import co.dds.cryptowallet.presentation.viewmodel.WalletAddressRoomDBViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoadGenerateWalletFragment : Fragment() {

    private var _binding: FragmentLoadGenerateWalletBinding? = null
    private val binding get() = _binding!!

    private val generateWalletViewModel: GenerateWalletViewModel by viewModel()
    private val walletAddressRoomDBViewModel: WalletAddressRoomDBViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoadGenerateWalletBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationShowSeedPhraseFragment()
    }

    private fun navigationShowSeedPhraseFragment() {
        CoroutineScope(Dispatchers.Main).launch {
            generateWalletViewModel.generateSeedPhrase().observe(viewLifecycleOwner) { seedPhrase ->
                TempData.seedPhrase = seedPhrase
                TempData.listSeedPhrase = seedPhrase.split(" ")

                generateWalletViewModel.generateCredential(seedPhrase, TempData.number)
                    .observe(viewLifecycleOwner) { credential ->
                        val walletViewModel = WalletAddressModel(
                            "${Const.account}${TempData.number + 1}",
                            credential.address,
                            credential.ecKeyPair.privateKey.toString(16)
                        )
                        checkAddressExists(walletViewModel)
                    }
            }
            delay(3000)
            findNavController().navigate(R.id.action_loadGenerateWalletFragment_to_seedPhraseShowFragment)
        }
    }

    private fun checkAddressExists(walletAddressModel: WalletAddressModel) {
        walletAddressRoomDBViewModel.checkAddressExists(walletAddressModel.address)
            .observe(viewLifecycleOwner) { exists ->
                if (!exists) {
                    walletAddressRoomDBViewModel.insertWalletAddress(walletAddressModel)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}