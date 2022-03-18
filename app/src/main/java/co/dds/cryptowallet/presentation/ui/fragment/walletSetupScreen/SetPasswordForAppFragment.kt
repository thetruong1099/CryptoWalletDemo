package co.dds.cryptowallet.presentation.ui.fragment.walletSetupScreen

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import co.dds.cryptowallet.R
import co.dds.cryptowallet.databinding.FragmentSetPasswordForAppBinding
import co.dds.cryptowallet.presentation.until.PasswordStrength
import co.dds.cryptowallet.presentation.until.sha256
import co.dds.cryptowallet.presentation.viewmodel.SharePreferenceViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class SetPasswordForAppFragment : Fragment() {

    private var _binding: FragmentSetPasswordForAppBinding? = null
    private val binding get() = _binding!!

    private val sharePreferenceViewModel: SharePreferenceViewModel by viewModel()

    private var statusShowPassword = false
    private var statusConfirmPassword = false
    private var statusStrengthPassword = false
    private var statusAgreePolicy = false
    private var statusSaveLogin = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSetPasswordForAppBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPasswordStrength()
        showPassword()
        checkPasswordConfirm()
        showButtonCreatePassword()
        createPassword()
        saveLoginApp()
        clickAgreePolicy()
        listenerFocusEditText()

        binding.checkBoxAgreePolicy.isChecked = statusAgreePolicy
    }

    private fun checkPasswordStrength() {
        binding.edtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                showIconConfirmPassword(
                    s.toString(),
                    binding.edtConfirmPassword.text.trim().toString()
                )
                showTextViewPasswordStrength(s.toString())
                updateTextPasswordStrength(s.toString())
                showButtonCreatePassword()
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

    @SuppressLint("SetTextI18n")
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

    private fun showPassword() {
        binding.btnShowPassword.setOnClickListener {
            if (statusShowPassword) {
                binding.edtPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding.edtConfirmPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                statusShowPassword = false
                binding.btnShowPassword.text = getText(R.string.hide)
            } else {
                binding.edtPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                binding.edtConfirmPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                statusShowPassword = true
                binding.btnShowPassword.text = getText(R.string.show)
            }
        }
    }

    private fun checkPasswordConfirm() {
        binding.edtConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                showIconConfirmPassword(binding.edtPassword.text.trim().toString(), s.toString())
                showButtonCreatePassword()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
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

    private fun showButtonCreatePassword() {
        if (statusAgreePolicy && statusConfirmPassword && statusStrengthPassword) {
            binding.btnCreatePassword.alpha = 1.0f
            binding.btnCreatePassword.setTextColor(Color.WHITE)
        } else {
            binding.btnCreatePassword.alpha = 0.5f
            binding.btnCreatePassword.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.blue_2
                )
            )
        }
    }

    private fun createPassword() {
        binding.btnCreatePassword.setOnClickListener {
            if (statusAgreePolicy && statusConfirmPassword && statusStrengthPassword) {
                val password = binding.edtPassword.text.trim().toString().sha256()
                sharePreferenceViewModel.setPassword(password)
                sharePreferenceViewModel.saveStatusLogin(statusSaveLogin)

                findNavController().navigate(
                    R.id.action_setPasswordForAppFragment_to_loadGenerateWalletFragment
                )
            } else {
                showDialog()
            }
        }
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(getString(R.string.check_password_strength_or_policy_again))
        builder.setPositiveButton(getString(R.string.yes)) { dialogInterface, which ->
            dialogInterface.cancel()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun saveLoginApp() {
        binding.switchRememberMe.setOnClickListener {
            statusSaveLogin = binding.switchRememberMe.isChecked
        }
    }

    private fun clickAgreePolicy() {
        binding.checkBoxAgreePolicy.setOnClickListener {
            statusAgreePolicy = binding.checkBoxAgreePolicy.isChecked
            showButtonCreatePassword()
        }
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager!!.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun listenerFocusEditText() {
        binding.edtPassword.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) hideKeyboard(v)
        }

        binding.edtConfirmPassword.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) hideKeyboard(v)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}