package co.dds.cryptowallet.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.dds.cryptowallet.databinding.LayoutNftItemBinding
import co.dds.cryptowallet.domain.model.localDatabase.NFTModel

class NftTokenAdapter() : RecyclerView.Adapter<NftTokenAdapter.NftTokenViewHolder>() {

    private var listNftToken = listOf<NFTModel>()

    inner class NftTokenViewHolder(
        private val binding: LayoutNftItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(nftModel: NFTModel) {
            binding.tvNameNft.text = nftModel.nftName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NftTokenViewHolder {
        val binding =
            LayoutNftItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NftTokenViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NftTokenViewHolder, position: Int) {
        holder.bind(listNftToken[position])
    }

    override fun getItemCount(): Int = listNftToken.size

    @SuppressLint("NotifyDataSetChanged")
    fun setListNftToken(listNftToken: List<NFTModel>) {
        this.listNftToken = listNftToken
        notifyDataSetChanged()
    }

}