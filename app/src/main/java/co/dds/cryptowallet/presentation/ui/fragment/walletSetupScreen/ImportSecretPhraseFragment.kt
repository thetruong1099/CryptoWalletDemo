package co.dds.cryptowallet.presentation.ui.fragment.walletSetupScreen

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
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
import co.dds.cryptowallet.R
import co.dds.cryptowallet.data.tempData.TempData
import co.dds.cryptowallet.databinding.FragmentImportSecretPhraseBinding
import co.dds.cryptowallet.domain.model.localDatabase.WalletAddressModel
import co.dds.cryptowallet.presentation.ui.activity.homeScreen.HomeActivity
import co.dds.cryptowallet.presentation.ui.activity.qrCode.QRCodeScannerActivity
import co.dds.cryptowallet.presentation.until.Const
import co.dds.cryptowallet.presentation.until.PasswordStrength
import co.dds.cryptowallet.presentation.until.sha256
import co.dds.cryptowallet.presentation.viewmodel.GenerateWalletViewModel
import co.dds.cryptowallet.presentation.viewmodel.SharePreferenceViewModel
import co.dds.cryptowallet.presentation.viewmodel.TempDataViewModel
import co.dds.cryptowallet.presentation.viewmodel.WalletAddressRoomDBViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ImportSecretPhraseFragment : Fragment() {
    private var _binding: FragmentImportSecretPhraseBinding? = null
    private val binding get() = _binding!!

    private var statusShowSecretPhrase = false
    private var statusShowPassword = false
    private var statusStrengthPassword = false
    private var statusConfirmPassword = false

    private val sharePreferenceViewModel: SharePreferenceViewModel by viewModel()
    private val generateWalletViewModel: GenerateWalletViewModel by viewModel()
    private val walletAddressRoomDBViewModel: WalletAddressRoomDBViewModel by viewModel()
    private val tempDataViewModel: TempDataViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentImportSecretPhraseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listenerEditTextForHideKeyBoard()
        setClickButtonShowSecretPhrase()
        setCLickButtonShowPassword()
        checkPasswordStrength()
        checkConfirmPassword()
        saveLogin()
        listenerButtonScan()
        importSecretPhrase()
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager!!.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun listenerEditTextForHideKeyBoard() {
        binding.edtSecretRecoveryPhrase.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) hideKeyboard(v)
        }

        binding.edtNewPassword.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) hideKeyboard(v)
        }

        binding.edtConfirmPassword.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) hideKeyboard(v)
        }
    }

    private fun setClickButtonShowSecretPhrase() {
        binding.btnShowSecretPhrase.setOnClickListener {
            if (statusShowSecretPhrase) {
                binding.edtSecretRecoveryPhrase.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding.btnShowSecretPhrase.text = getString(R.string.hide)
                statusShowSecretPhrase = false
            } else {
                binding.edtSecretRecoveryPhrase.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                binding.btnShowSecretPhrase.text = getString(R.string.show)
                statusShowSecretPhrase = true
            }
        }
    }

    private fun setCLickButtonShowPassword() {
        binding.btnShowPassword.setOnClickListener {
            if (statusShowPassword) {
                binding.edtNewPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding.edtConfirmPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding.btnShowPassword.text = getString(R.string.hide)
                statusShowPassword = false
            } else {
                binding.edtNewPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                binding.edtConfirmPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                binding.btnShowPassword.text = getString(R.string.show)
                statusShowPassword = true
            }
        }
    }

    private fun checkPasswordStrength() {
        binding.edtNewPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val confirmPassword = binding.edtConfirmPassword.text.toString()
                showIconConfirmPassword(s.toString(), confirmPassword)
                showTextViewPasswordStrength(s.toString())
                updateTextPasswordStrength(s.toString())
                showButtonImport()
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

    private fun showButtonImport(): Boolean {
        return if (statusConfirmPassword && statusStrengthPassword && binding.edtSecretRecoveryPhrase.text.trim()
                .toString().isNotEmpty()
        ) {
            binding.btnImport.alpha = 1f
            true
        } else {
            binding.btnImport.alpha = 0.5f
            false
        }
    }

    private fun checkConfirmPassword() {
        binding.edtConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val password = binding.edtNewPassword.text.trim().toString()
                showIconConfirmPassword(password, s.toString())
                showButtonImport()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun showIconConfirmPassword(password: String, confirmPassword: String) {
        if (password == confirmPassword) {
            statusConfirmPassword = true
            binding.imgConfirmPassword.visibility = View.VISIBLE
        } else {
            statusConfirmPassword = false
            binding.imgConfirmPassword.visibility = View.GONE
        }
    }

    private fun saveLogin() {
        binding.switchRememberMe.setOnClickListener {
            sharePreferenceViewModel.saveStatusLogin(binding.switchRememberMe.isChecked)
        }
    }

    private fun listenerButtonScan() {
        binding.btnScan.setOnClickListener {
            val intent = Intent(requireContext(), QRCodeScannerActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        setTextInEditTextSeedPhrase()
    }

    private fun setTextInEditTextSeedPhrase() {
        tempDataViewModel.getCodeLiveData().observe(viewLifecycleOwner) {
            binding.edtSecretRecoveryPhrase.setText(it)
        }
    }

    private fun importSecretPhrase() {
        binding.btnImport.setOnClickListener {
            val mnemonic = binding.edtSecretRecoveryPhrase.text.trim().toString()
            if (showButtonImport()) {
                generateWalletViewModel.checkValidateMnemonic(mnemonic)
                    .observe(viewLifecycleOwner) { validate ->
                        if (validate) {
                            TempData.seedPhrase = mnemonic
                            sharePreferenceViewModel.saveMnemonic(mnemonic)
                            generateWalletViewModel.generateCredential(mnemonic, TempData.number)
                                .observe(viewLifecycleOwner) { credential ->
                                    val walletAddressModel = WalletAddressModel(
                                        "${Const.account}${TempData.number + 1}",
                                        credential.address,
                                        credential.ecKeyPair.privateKey.toString(16)
                                    )
                                    checkAddressExists(walletAddressModel)
                                    savePassword()
                                }
                        } else {
                            showDialog()
                        }
                    }
            }
        }
    }

    private fun checkAddressExists(walletAddressModel: WalletAddressModel) {
        walletAddressRoomDBViewModel.checkAddressExists(walletAddressModel.address)
            .observe(viewLifecycleOwner) { exists ->
                if (!exists) {
                    walletAddressRoomDBViewModel.insertWalletAddress(walletAddressModel)
                }
                startToHomeActivity()
            }
    }

    private fun startToHomeActivity() {
        val intent = Intent(requireActivity(), HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }

        requireActivity().apply {
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(getString(R.string.check_secret_phrase_password_again))
        builder.setPositiveButton(getString(R.string.yes)) { dialogInterface, which ->
            dialogInterface.cancel()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun savePassword() {
        val password = binding.edtNewPassword.text.trim().toString().sha256()
        sharePreferenceViewModel.setPassword(password)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}