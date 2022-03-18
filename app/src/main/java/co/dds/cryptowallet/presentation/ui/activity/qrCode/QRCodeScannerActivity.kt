package co.dds.cryptowallet.presentation.ui.activity.qrCode

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import co.dds.cryptowallet.databinding.ActivityQrcodeScanerBinding
import co.dds.cryptowallet.presentation.viewmodel.TempDataViewModel
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class QRCodeScannerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQrcodeScanerBinding

    private val tempDataViewModel: TempDataViewModel by viewModel()

    companion object {
        private const val PERMISSION_CAMERA_REQUEST = 1
        private const val RATIO_4_3_VALUE = 4.0 / 3.0
        private const val RATIO_16_9_VALUE = 16.0 / 9.0
    }

    private var cameraProvider: ProcessCameraProvider? = null
    private var cameraSelector: CameraSelector? = null
    private var lensFacing = CameraSelector.LENS_FACING_BACK
    private var previewUseCase: Preview? = null
    private var analysisUseCase: ImageAnalysis? = null
    private val screenAspectRatio: Int
        get() {
            // Get screen metrics used to setup camera for full screen resolution
            val metrics = DisplayMetrics().also { binding.viewFinder.display?.getRealMetrics(it) }
            return aspectRatio(metrics.widthPixels, metrics.heightPixels)
        }


    private val requestPermissionCamera = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            setupCamera()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrcodeScanerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideSystemUI()
        requestPermission()
    }

    private fun hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }

    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun setupCamera() {
        cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(
            {
                try {
                    cameraProvider = cameraProviderFuture.get()
                    if (isCameraPermissionGranted()) {
                        bindCameraUseCases()
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(
                                arrayOf(Manifest.permission.CAMERA),
                                PERMISSION_CAMERA_REQUEST
                            )
                        }
                    }
                } catch (e: ExecutionException) {
                    // Handle any errors (including cancellation) here.
                    Log.e("QrScanViewModel", "Unhandled exception", e)
                } catch (e: InterruptedException) {
                    Log.e("QrScanViewModel", "Unhandled exception", e)
                }
            },
            ContextCompat.getMainExecutor(this)
        )
    }

    private fun bindCameraUseCases() {
        bindPreviewUseCase()
        bindAnalyseUseCase()
    }

    private fun bindPreviewUseCase() {
        if (cameraProvider == null) {
            return
        }
        if (previewUseCase != null) {
            cameraProvider?.unbind(previewUseCase)
        }

        previewUseCase = Preview.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(binding.viewFinder.display.rotation)
            .build()

        previewUseCase?.setSurfaceProvider(binding.viewFinder.surfaceProvider)

        try {
            cameraSelector?.let {
                cameraProvider?.bindToLifecycle(this, it, previewUseCase)
            }
        } catch (illegalStateException: IllegalStateException) {
        } catch (illegalArgumentException: IllegalArgumentException) {
        }
    }

    private fun bindAnalyseUseCase() {
        val barcodeScanner: BarcodeScanner = BarcodeScanning.getClient()

        if (cameraProvider == null) {
            return
        }
        if (analysisUseCase != null) {
            cameraProvider?.unbind(analysisUseCase)
        }

        analysisUseCase = ImageAnalysis.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(binding.viewFinder.display.rotation)
            .build()

        // Initialize our background executor
        val cameraExecutor = Executors.newSingleThreadExecutor()

        analysisUseCase?.setAnalyzer(cameraExecutor) { imageProxy ->
            processImageProxy(barcodeScanner, imageProxy)
        }

        try {
            cameraSelector?.let {
                cameraProvider?.bindToLifecycle(/* lifecycleOwner= */this,
                    it, analysisUseCase
                )
            }
        } catch (illegalStateException: IllegalStateException) {
        } catch (illegalArgumentException: IllegalArgumentException) {
        }
    }

    @SuppressLint("UnsafeExperimentalUsageError", "UnsafeOptInUsageError")
    private fun processImageProxy(
        barcodeScanner: BarcodeScanner,
        imageProxy: ImageProxy
    ) {
        if (imageProxy.image == null) return
        val inputImage =
            InputImage.fromMediaImage(imageProxy.image!!, imageProxy.imageInfo.rotationDegrees)

        barcodeScanner.process(inputImage)
            .addOnSuccessListener { barcodes ->
                val barcode = barcodes.getOrNull(0)
                barcode?.rawValue?.let { code ->
                    backToFragmentClickScanner(code)
                }
            }
            .addOnFailureListener {

            }.addOnCompleteListener {
                imageProxy.close()
            }
    }


    private fun isCameraPermissionGranted(): Boolean = this.let {
        ContextCompat.checkSelfPermission(it, Manifest.permission.CAMERA)
    } == PackageManager.PERMISSION_GRANTED

    @SuppressLint("ObsoleteSdkInt")
    private fun requestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setupCamera()
        } else {
            val resultCameraPermission =
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)

            if (resultCameraPermission == PackageManager.PERMISSION_GRANTED) {
                setupCamera()
            } else {
                requestPermissionCamera.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun backToFragmentClickScanner(code: String) {
        CoroutineScope(Dispatchers.Main).launch {
            tempDataViewModel.setCodeLiveData(code)
            delay(200)
            finish()
        }

    }
}