package co.dds.cryptowallet.presentation.ui.fragment.settingScreen

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import co.dds.cryptowallet.R
import co.dds.cryptowallet.databinding.FragmentConfirmPasswordBinding
import co.dds.cryptowallet.presentation.until.NavigationFragmentSetting
import co.dds.cryptowallet.presentation.until.sha256
import co.dds.cryptowallet.presentation.viewmodel.SharePreferenceViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ConfirmPasswordFragment : Fragment() {

    private var _binding: FragmentConfirmPasswordBinding? = null
    private val binding get() = _binding!!

    private val sharePreferenceViewModel: SharePreferenceViewModel by viewModel()
    private var navigation: NavigationFragmentSetting? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentConfirmPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataFormArgument()
        confirmPassword()
        setButtonCancel()
        listenerEditText()
    }

    private fun getDataFormArgument() {
        arguments?.let {
            navigation = ConfirmPasswordFragmentArgs.fromBundle(
                it
            ).dataOfSettingFragment
        }
    }

    private fun confirmPassword() {
        binding.btnNext.setOnClickListener {
            val passwordInput = binding.edtPassword.text.trim().toString().sha256()
            sharePreferenceViewModel.getPassword().observe(viewLifecycleOwner) { password ->
                if (password == passwordInput) {
                    setNavigationToFragment()
                } else {
                    binding.tvPasswordNotCorrect.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setNavigationToFragment() {
        when (navigation) {
            NavigationFragmentSetting.SHOW_SECRET_PHRASE -> {
                findNavController().navigate(R.id.action_confirmPasswordFragment_to_showSecretRecoveryPhraseFragment)
            }

            NavigationFragmentSetting.CHANGE_PASSWORD -> {
                findNavController().navigate(R.id.action_confirmPasswordFragment_to_changePasswordFragment)
            }

            NavigationFragmentSetting.SHOW_PRIVATE_KEY -> {
                findNavController().navigate(R.id.action_confirmPasswordFragment_to_showPrivateKeyFragment)
            }
            else -> {}
        }
    }

    private fun setButtonCancel() {
        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager!!.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun listenerEditText() {
        binding.edtPassword.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) hideKeyboard(v)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}