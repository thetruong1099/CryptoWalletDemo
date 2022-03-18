package co.dds.cryptowallet.presentation.ui.fragment.showScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.dds.cryptowallet.databinding.FragmentShowTextBinding


class ShowTextFragment(
    private val data: String,
    private val notification: String
) : Fragment() {
    private var _binding: FragmentShowTextBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentShowTextBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvSecretRecoveryPhrase.text = data
        binding.tvNotification.text = notification
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}