package com.areeb.whatsappstatussaver.ui.base.fragments

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.storage.StorageManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.areeb.whatsappstatussaver.utils.constants.Constants
import com.areeb.whatsappstatussaver.utils.sharedPrefernces.SharedPrefences

open class BaseFragments : Fragment() {

    companion object {
        private const val INITIAL_KEY = "android.provider.extra.INITIAL_URI"
        private const val ADVANCE_KEY = "android.content.SHOW_ADVANCED"
        private const val REQUEST_CODE = 1234
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun getFolderPermission() {
        val storageManager =
            requireActivity().getSystemService(Context.STORAGE_SERVICE) as StorageManager
        val intent = storageManager.primaryStorageVolume.createOpenDocumentTreeIntent()
        val statusUri = Uri.parse(Constants.TARGET_DIRECTORY.TARGET_DIRECTORY_KEY)
        intent.putExtra(INITIAL_KEY, statusUri)
        intent.putExtra(ADVANCE_KEY, true)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val treeUri = data?.data
            SharedPrefences.setIsFolderSelected(requireContext(), true)
        }
    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
