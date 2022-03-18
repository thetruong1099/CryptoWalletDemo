package co.dds.cryptowallet.presentation.ui.fragment.importScreen

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import co.dds.cryptowallet.R
import co.dds.cryptowallet.data.tempData.TempData
import co.dds.cryptowallet.databinding.FragmentImportNftBinding
import co.dds.cryptowallet.domain.model.localDatabase.NFTModel
import co.dds.cryptowallet.presentation.until.Status
import co.dds.cryptowallet.presentation.viewmodel.NFTViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ImportNftFragment : Fragment() {

    private var _binding: FragmentImportNftBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NFTViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        _binding = FragmentImportNftBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenerEditTextForHideKeyboard()
        listenerButtonCancel()
        listenerButtonImport()
    }

    private fun listenerEditTextForHideKeyboard() {
        binding.edtContractAddress.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeyboard(v)
                showNotificationForEdtContractAddress()
            }
        }

        binding.edtTokenId.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeyboard(v)
                showNotificationForEdtTokenId()
            }
        }

    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager!!.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun showNotificationForEdtContractAddress() {
        if (binding.edtContractAddress.text.isEmpty()) {
            binding.tvEdtContractAddressEmptyNotification.visibility = View.VISIBLE
        }
    }

    private fun showNotificationForEdtTokenId() {
        if (binding.edtTokenId.text.isEmpty()) {
            binding.tvEdtTokenIdEmptyNotification.visibility = View.VISIBLE
        }
    }

    private fun listenerButtonCancel() {
        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack(R.id.walletHomeFragment, false)
        }
    }

    private fun listenerButtonImport() {
        binding.btnImport.setOnClickListener { view ->
            hideKeyboard(view)
            val contractAddress = binding.edtContractAddress.text.trim().toString()
            val tokenId = binding.edtTokenId.text.trim().toString()

            if (contractAddress.isNotEmpty() && tokenId.isNotEmpty()) {
                viewModel.getNftMetadata(
                    "ETH",
                    contractAddress,
                    tokenId,
                    TempData.addressModel!!.address
                ).observe(viewLifecycleOwner) { resource ->
                    when (resource.status) {
                        Status.LOADING -> {
                            binding.animationStatusImportNft.visibility = View.VISIBLE
                        }

                        Status.ERROR -> {
                            setAnimationFail()
                            Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT)
                                .show()
                        }

                        Status.SUCCESS -> {
                            val dataUrl = resource.data!!.data
                            val id = dataUrl.split("/").last()
                            viewModel.getNftFileFromIPFS(id)
                                .observe(viewLifecycleOwner) { resources ->
                                    when (resources.status) {
                                        Status.ERROR -> {
                                            setAnimationFail()

                                            Toast.makeText(
                                                requireContext(),
                                                resources.message,
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()
                                        }

                                        Status.SUCCESS -> {
                                            if (resources.data != null) {
                                                val nftToken = NFTModel(
                                                    resources.data.name,
                                                    tokenId,
                                                    contractAddress,
                                                    resources.data.url
                                                )
                                                viewModel.insertNftToken(nftToken)
                                                setAnimationSuccess()
                                                while (true) {
                                                    binding.animationStatusImportNft.apply {
                                                        if (progress >= 1) {
                                                            findNavController().popBackStack(
                                                                R.id.walletHomeFragment,
                                                                false
                                                            )
                                                        }
                                                    }
                                                }
                                            } else {
                                                setAnimationFail()
                                            }
                                        }
                                        else -> {

                                        }
                                    }
                                }
                        }
                    }
                }
            } else {
                showNotificationForEdtContractAddress()
                showNotificationForEdtTokenId()
            }
        }
    }

    private fun setAnimationFail() {
        binding.animationStatusImportNft.apply {
            setAnimation(R.raw.fail_animation)
            repeatCount = 0
            playAnimation()
        }
    }

    private fun setAnimationSuccess() {
        binding.animationStatusImportNft.apply {
            setAnimation(R.raw.correct_animation)
            repeatCount = 0
            playAnimation()
        }
    }
}