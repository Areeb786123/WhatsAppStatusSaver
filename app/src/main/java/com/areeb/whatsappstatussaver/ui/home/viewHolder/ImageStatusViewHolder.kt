package com.areeb.whatsappstatussaver.ui.home.viewHolder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.areeb.whatsappstatussaver.data.models.StatusDto
import com.areeb.whatsappstatussaver.databinding.ItemsSaverBinding
import com.areeb.whatsappstatussaver.ui.common.OnItemClick
import com.bumptech.glide.Glide

class ImageStatusViewHolder(private val binding: ItemsSaverBinding) :
    RecyclerView.ViewHolder(binding.root), View.OnClickListener {
    init {
        binding.itemStatus.setOnClickListener(this)
    }

    private lateinit var onItemClick: OnItemClick
    private lateinit var statusDto: StatusDto
    fun bind(statusDto: StatusDto, onItemClick: OnItemClick, isPhotoFragment: Boolean) {
        this.onItemClick = onItemClick
        this.statusDto = statusDto

        Glide.with(binding.root.context).load(statusDto.fileUri).into(binding.itemStatus)
        handlingForImageIcon(isPhotoFragment)
    }

    private fun handlingForImageIcon(value: Boolean) {
        if (value) {
            binding.videoIcon.visibility = View.VISIBLE
            binding.itemStatus.alpha = 0.4f
        }
    }

    companion object {
        fun from(viewGroup: ViewGroup): ImageStatusViewHolder {
            return ImageStatusViewHolder(
                ItemsSaverBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false),
            )
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            binding.itemStatus.id -> {
                onItemClick.onItemClick(statusDto)
            }
        }
    }
}
