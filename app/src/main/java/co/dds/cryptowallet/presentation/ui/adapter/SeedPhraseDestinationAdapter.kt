package co.dds.cryptowallet.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.dds.cryptowallet.R
import co.dds.cryptowallet.databinding.LayoutSeedPhraseConfirmDestinationItemBinding
import co.dds.cryptowallet.presentation.until.Const

class SeedPhraseDestinationAdapter(
    private val clickTextWord: (String) -> Unit,
) : RecyclerView.Adapter<SeedPhraseDestinationAdapter.SeedPhraseDestinationViewHolder>() {

    private val listSeedPhraseDestination =
        mutableListOf("", "", "", "", "", "", "", "", "", "", "", "")

    private val statusIndexWord = mutableMapOf<Int, Boolean>()

    inner class SeedPhraseDestinationViewHolder(
        private val binding: LayoutSeedPhraseConfirmDestinationItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun setTextNumber(position: Int) {
            binding.tvNumber.text = "${position + 1}."
        }

        fun setTextWord(word: String, position: Int) {
            binding.tvSeedWord.text = word
            if (word != "") {
                binding.tvSeedWord.setOnClickListener {
                    clickTextWord(word)
                    setBackgroundTextWord(position)
                    removeFormListSeedPhraseDestination(word)
                }
            }
        }

        fun setBackgroundTextWord(position: Int) {
            statusIndexWord[position]?.let { status ->
                if (status) {
                    binding.tvSeedWord.setBackgroundResource(R.drawable.background_one)
                } else {
                    binding.tvSeedWord.setBackgroundResource(R.drawable.background_textview_four)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SeedPhraseDestinationViewHolder {
        val binding = LayoutSeedPhraseConfirmDestinationItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SeedPhraseDestinationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SeedPhraseDestinationViewHolder, position: Int) {
        holder.setTextNumber(position)
        if (listSeedPhraseDestination.isNotEmpty() && listSeedPhraseDestination.size > position) {
            holder.setTextWord(listSeedPhraseDestination[position], position)
        }
        holder.setBackgroundTextWord(position)
    }

    override fun getItemCount(): Int = Const.sizeSeedAdapter

    fun addToListSeedPhraseDestination(word: String) {
        for ((index, value) in listSeedPhraseDestination.withIndex()) {
            if (value == "") {
                listSeedPhraseDestination[index] = word
                statusIndexWord[index] = true
                notifyItemChanged(index)
                break
            }
        }
    }

    fun removeFormListSeedPhraseDestination(word: String) {
        for ((index, value) in listSeedPhraseDestination.withIndex()) {
            if (value == word) {
                listSeedPhraseDestination[index] = ""
                statusIndexWord[index] = false
                notifyItemChanged(index)
                break
            }
        }
    }
}