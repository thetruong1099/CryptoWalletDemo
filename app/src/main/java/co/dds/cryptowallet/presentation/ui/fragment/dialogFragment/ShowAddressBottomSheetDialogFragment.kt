package co.dds.cryptowallet.presentation.ui.fragment.dialogFragment

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import co.dds.cryptowallet.R
import co.dds.cryptowallet.data.tempData.TempData
import co.dds.cryptowallet.databinding.FragmentShowAddressBottomSheetDialogBinding
import co.dds.cryptowallet.presentation.until.Const
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter


class ShowAddressBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentShowAddressBottomSheetDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentShowAddressBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        generateQRCode()
        setTextAddress()
        listenerButtonCopy()
    }

    private fun generateQRCode() {
        val writer = QRCodeWriter()
        try {
            val bitMatrix =
                writer.encode(TempData.addressModel?.address, BarcodeFormat.QR_CODE, 1024, 1024)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            binding.imgQrCode.setImageBitmap(bmp)
        } catch (e: WriterException) {
            Log.d("exception", "generateQRCode: ${e.message}")
        }
    }

    private fun setTextAddress() {
        TempData.addressModel?.let {
            binding.tvAddress.text = it.address
        }
    }

    private fun listenerButtonCopy() {
        binding.btnCopy.setOnClickListener {
            val clipboardManager =
                requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText(Const.COPY_LABEL, binding.tvAddress.text)
            clipboardManager.setPrimaryClip(clipData)

            Toast.makeText(requireContext(), getString(R.string.copy_success), Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}