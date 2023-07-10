package com.areeb.whatsappstatussaver.ui.home.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.areeb.whatsappstatussaver.data.models.StatusDto
import com.areeb.whatsappstatussaver.databinding.ItemsSaverBinding
import com.bumptech.glide.Glide

class ImageStatusViewHolder(private val binding: ItemsSaverBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(statusDto: StatusDto) {
        Glide.with(binding.root.context).load(statusDto.fileUri).into(binding.itemStatus)
    }

    companion object {
        fun from(viewGroup: ViewGroup): ImageStatusViewHolder {
            return ImageStatusViewHolder(
                ItemsSaverBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false),
            )
        }
    }
}
