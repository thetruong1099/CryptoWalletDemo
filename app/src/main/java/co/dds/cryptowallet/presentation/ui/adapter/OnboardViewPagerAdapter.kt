package co.dds.cryptowallet.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.dds.cryptowallet.R
import co.dds.cryptowallet.databinding.LayoutOnboardBinding

class OnboardViewPagerAdapter : RecyclerView.Adapter<OnboardViewPagerAdapter.OnboardViewPagerViewHolder>() {

    private val listTitle = arrayOf(
        R.string.welcome_to_metamask,
        R.string.manage_your_digital_assets,
        R.string.your_gateway_to_web3
    )

    private val listDescription = arrayOf(
        R.string.metamask_introduce,
        R.string.manage_assets_description,
        R.string.gateway_to_web3_description
    )

    private val listImage = arrayOf(
        R.drawable.onboard_carousel_1,
        R.drawable.onboard_carousel_2,
        R.drawable.onboard_carousel_3
    )

    inner class OnboardViewPagerViewHolder(
        private val binding: LayoutOnboardBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(title: Int, description: Int, imageResource: Int) {
            binding.textTitle.setText(title)
            binding.textDescription.setText(description)
            binding.imgOnboard.setImageResource(imageResource)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardViewPagerViewHolder {
        val binding =
            LayoutOnboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OnboardViewPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OnboardViewPagerViewHolder, position: Int) {
        holder.bind(listTitle[position], listDescription[position], listImage[position])
    }

    override fun getItemCount(): Int = listTitle.size

}