package com.areeb.whatsappstatussaver.utils.sharedPrefernces

import android.content.Context
import com.areeb.whatsappstatussaver.utils.constants.Constants.TARGET_DIRECTORY.SHARED_PEREFENCES.Companion.IS_FOLDER_SELECTED
import com.areeb.whatsappstatussaver.utils.constants.Constants.TARGET_DIRECTORY.SHARED_PEREFENCES.Companion.SHARED_PEREFENCES

object SharedPrefences {
    fun setIsFolderSelected(context: Context, value: Boolean) {
        val sp = context.getSharedPreferences(SHARED_PEREFENCES, Context.MODE_PRIVATE)
        sp.edit().putBoolean(IS_FOLDER_SELECTED, value)
    }

    fun isFolderSelected(context: Context): Boolean {
        val sp = context.getSharedPreferences(SHARED_PEREFENCES, Context.MODE_PRIVATE)
        if (sp.contains(IS_FOLDER_SELECTED)) {
            return true
        }

        return false
    }
}
