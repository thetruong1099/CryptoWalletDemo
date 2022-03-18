package co.dds.cryptowallet.presentation.ui.fragment.settingScreen

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import co.dds.cryptowallet.R
import co.dds.cryptowallet.databinding.FragmentChangePasswordBinding
import co.dds.cryptowallet.presentation.until.PasswordStrength
import co.dds.cryptowallet.presentation.viewmodel.SharePreferenceViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ChangePasswordFragment : Fragment() {

    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!

    private var statusShowPassword = false
    private var statusStrengthPassword = false
    private var statusConfirmPassword = false

    private val sharePreferenceViewModel: SharePreferenceViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickButtonShowPassword()
        listenerEdtPassword()
        listenerEdtConfirmPassword()
        listenerButtonResetPassword()
    }

    private fun setClickButtonShowPassword() {
        binding.btnShowPassword.setOnClickListener {
            if (statusShowPassword) {
                binding.edtPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding.edtConfirmPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding.btnShowPassword.text = getString(R.string.hide)
                statusShowPassword = false
            } else {
                binding.edtPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                binding.edtConfirmPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                binding.btnShowPassword.text = getString(R.string.show)
                statusShowPassword = true
            }
        }
    }

    private fun listenerEdtPassword() {
        binding.edtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                showIconConfirmPassword(s.toString(), binding.edtConfirmPassword.text.toString())
                showTextViewPasswordStrength(s.toString())
                updateTextPasswordStrength(s.toString())
                showButtonResetPassword()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun listenerEdtConfirmPassword() {
        binding.edtConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                showIconConfirmPassword(s.toString(), binding.edtConfirmPassword.text.toString())
                showTextViewPasswordStrength(s.toString())
                updateTextPasswordStrength(s.toString())
                showButtonResetPassword()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun showTextViewPasswordStrength(password: String) {
        if (password.isNotEmpty()) {
            binding.tvPasswordStrength.visibility = View.VISIBLE
            binding.tvPasswordStrengthProperties.visibility = View.VISIBLE
        } else {
            binding.tvPasswordStrength.visibility = View.GONE
            binding.tvPasswordStrengthProperties.visibility = View.GONE
        }
    }

    private fun updateTextPasswordStrength(password: String) {
        val passwordStrength = PasswordStrength.calculateStrength(password)
        binding.tvPasswordStrength.text = getText(R.string.password_strength)
        binding.tvPasswordStrengthProperties.text = passwordStrength.getText(requireContext())
        binding.tvPasswordStrengthProperties.setTextColor(passwordStrength.color)

        statusStrengthPassword =
            passwordStrength.getText(requireContext()) == getString(R.string.password_strength_medium) ||
                    passwordStrength.getText(requireContext()) == getText(R.string.password_strength_strong) ||
                    passwordStrength.getText(requireContext()) == getText(R.string.password_strength_very_strong)

    }

    private fun showIconConfirmPassword(password: String, confirmPassword: String) {
        if (password == confirmPassword && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
            statusConfirmPassword = true
            binding.imgConfirmPassword.visibility = View.VISIBLE
        } else {
            statusConfirmPassword = false
            binding.imgConfirmPassword.visibility = View.GONE
        }
    }

    private fun showButtonResetPassword(): Boolean {
        return if (statusConfirmPassword && statusStrengthPassword) {
            binding.btnResetPassword.alpha = 1f
            true
        } else {
            binding.btnResetPassword.alpha = 0.5f
            false
        }
    }

    private fun listenerButtonResetPassword() {
        binding.btnResetPassword.setOnClickListener {
            if (showButtonResetPassword()) {
                sharePreferenceViewModel.setPassword(binding.edtPassword.text.trim().toString())
                findNavController().popBackStack(R.id.settingFragment, false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}