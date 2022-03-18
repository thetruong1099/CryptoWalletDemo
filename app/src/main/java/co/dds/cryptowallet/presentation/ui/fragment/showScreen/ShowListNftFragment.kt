package co.dds.cryptowallet.presentation.ui.fragment.showScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import co.dds.cryptowallet.R
import co.dds.cryptowallet.databinding.FragmentListNftBinding


class ShowListNftFragment : Fragment() {
    private var _binding: FragmentListNftBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentListNftBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenerButtonImportNft()

    }

    private fun listenerButtonImportNft() {
        binding.btnImportNft.setOnClickListener {
            findNavController().navigate(R.id.action_walletHomeFragment_to_importNftFragment)
        }
    }
}