package co.dds.cryptowallet.presentation.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.dds.cryptowallet.R
import co.dds.cryptowallet.databinding.LayoutSeedPhraseConfirmSourceItemBinding
import co.dds.cryptowallet.presentation.until.Const

class SeedPhraseSourceAdapter(
    private val clickTextWord: (String, Boolean) -> Unit,
    private val context: Context
) : RecyclerView.Adapter<SeedPhraseSourceAdapter.SeedPhraseSourceViewHolder>() {

    private var listSeedPhraseSource = listOf<String>()
    private var indexIsClick = ArrayList<Int>()

    inner class SeedPhraseSourceViewHolder(
        private val binding: LayoutSeedPhraseConfirmSourceItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("UseCompatLoadingForDrawables")
        fun onBind(word: String, position: Int) {
            binding.tvSeedWord.text = word
            binding.tvSeedWord.setOnClickListener {
                if (indexIsClick.contains(position)) {
                    clickTextWord(word, false)
                    indexIsClick.remove(position)
                    binding.tvSeedWord.setBackgroundResource(R.drawable.background_one)
                    binding.tvSeedWord.setTextColor(context.getColor(R.color.gray_1))
                } else {
                    indexIsClick.add(position)
                    clickTextWord(word, true)
                    binding.tvSeedWord.setBackgroundResource(R.drawable.background_textview_one)
                    binding.tvSeedWord.setTextColor(context.getColor(R.color.white))
                }
            }
        }

        fun setBackgroundTextWord(position: Int) {
            if (indexIsClick.contains(position)) {
                binding.tvSeedWord.setBackgroundResource(R.drawable.background_textview_one)
                binding.tvSeedWord.setTextColor(context.getColor(R.color.white))
            } else {
                binding.tvSeedWord.setBackgroundResource(R.drawable.background_one)
                binding.tvSeedWord.setTextColor(context.getColor(R.color.gray_1))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeedPhraseSourceViewHolder {
        val binding = LayoutSeedPhraseConfirmSourceItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SeedPhraseSourceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SeedPhraseSourceViewHolder, position: Int) {
        holder.onBind(listSeedPhraseSource[position], position)
        holder.setBackgroundTextWord(position)
    }

    override fun getItemCount(): Int = Const.sizeSeedAdapter

    fun setListSeedPhraseSource(listSeedPhraseSource: List<String>) {
        this.listSeedPhraseSource = listSeedPhraseSource
    }

    @SuppressLint("NotifyDataSetChanged")
    fun changItemRowIndex(word: String) {
        val index = listSeedPhraseSource.indexOf(word)
        if (indexIsClick.contains(index)) {
            indexIsClick.remove(index)
            notifyDataSetChanged()
        }
    }
}