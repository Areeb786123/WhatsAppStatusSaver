package com.areeb.whatsappstatussaver.ui.common

import com.areeb.whatsappstatussaver.data.models.StatusDto

interface OnItemClick {
    fun onItemClick(statusDto: StatusDto)
}
