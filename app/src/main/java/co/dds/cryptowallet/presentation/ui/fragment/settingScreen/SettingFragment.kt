package co.dds.cryptowallet.presentation.ui.fragment.settingScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import co.dds.cryptowallet.R
import co.dds.cryptowallet.databinding.FragmentSettingBinding
import co.dds.cryptowallet.presentation.until.NavigationFragmentSetting


class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickButtonSecretRecoveryPhrase()
        setClickButtonChangePassword()
        setClickButtonShowPrivatePassword()
    }

    private fun setClickButtonSecretRecoveryPhrase() {
        binding.btnRevealSecretRecoveryPhrase.setOnClickListener {
            val action =
                SettingFragmentDirections.actionSettingFragmentToConfirmPasswordFragment(
                    NavigationFragmentSetting.SHOW_SECRET_PHRASE
                )
            findNavController().navigate(action)
        }
    }

    private fun setClickButtonChangePassword() {
        binding.btnChangePassword.setOnClickListener {
            val action =
                SettingFragmentDirections.actionSettingFragmentToConfirmPasswordFragment(
                    NavigationFragmentSetting.CHANGE_PASSWORD
                )
            findNavController().navigate(action)
        }
    }

    private fun setClickButtonShowPrivatePassword() {
        binding.btnShowPrivateKey.setOnClickListener {
            val action =
                SettingFragmentDirections.actionSettingFragmentToConfirmPasswordFragment(
                    NavigationFragmentSetting.SHOW_PRIVATE_KEY
                )
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}