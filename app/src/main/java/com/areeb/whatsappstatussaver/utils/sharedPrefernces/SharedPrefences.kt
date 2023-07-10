package com.areeb.whatsappstatussaver.utils.sharedPrefernces

import android.content.Context
import com.areeb.whatsappstatussaver.utils.constants.Constants.TARGET_DIRECTORY.SHARED_PEREFENCES.Companion.IS_FOLDER_SELECTED
import com.areeb.whatsappstatussaver.utils.constants.Constants.TARGET_DIRECTORY.SHARED_PEREFENCES.Companion.SHARED_PEREFENCES
import com.areeb.whatsappstatussaver.utils.constants.Constants.TARGET_DIRECTORY.SHARED_PEREFENCES.Companion.TREE_URI_PATH

object SharedPrefences {
    fun setIsFolderSelected(context: Context, value: Boolean) {
        val sp = context.getSharedPreferences(SHARED_PEREFENCES, Context.MODE_PRIVATE)
        sp.edit().putBoolean(IS_FOLDER_SELECTED, value).apply()
    }

    fun isFolderSelected(context: Context): Boolean {
        val sp = context.getSharedPreferences(SHARED_PEREFENCES, Context.MODE_PRIVATE)
        if (sp.contains(IS_FOLDER_SELECTED)) {
            return true
        }

        return false
    }

    fun treeUriPath(context: Context, treeUriPath: String) {
        val sp = context.getSharedPreferences(SHARED_PEREFENCES, Context.MODE_PRIVATE)
        sp.edit().putString(TREE_URI_PATH, treeUriPath).apply()
    }

    fun getTreeUriPath(context: Context): String {
        val sp = context.getSharedPreferences(SHARED_PEREFENCES, Context.MODE_PRIVATE)
        if (!sp.contains(TREE_URI_PATH)) {
            return "null"
        }
        return sp.getString(TREE_URI_PATH, "") ?: ""
    }
}
