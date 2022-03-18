package co.dds.cryptowallet.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.dds.cryptowallet.databinding.LayoutSeedPhraseItemBinding

class SeedPhraseAdapter() : RecyclerView.Adapter<SeedPhraseAdapter.SeedPhraseViewHolder>() {

    private var listWordSeedPhrase = listOf<String>()

    inner class SeedPhraseViewHolder(
        private val binding: LayoutSeedPhraseItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(word: String, position: Int) {
            binding.tvSeedWord.text = "${position + 1}. $word"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeedPhraseViewHolder {
        val binding =
            LayoutSeedPhraseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeedPhraseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SeedPhraseViewHolder, position: Int) {
        holder.bind(listWordSeedPhrase[position], position)
    }

    override fun getItemCount(): Int = listWordSeedPhrase.size

    fun setListWordSeedPhase(listWord: List<String>) {
        this.listWordSeedPhrase = listWord
    }
}