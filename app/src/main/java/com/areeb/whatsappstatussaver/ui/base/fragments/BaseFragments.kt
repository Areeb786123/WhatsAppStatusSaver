package com.areeb.whatsappstatussaver.ui.base.fragments

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.storage.StorageManager
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.areeb.whatsappstatussaver.data.models.StatusDto
import com.areeb.whatsappstatussaver.utils.constants.Constants
import com.areeb.whatsappstatussaver.utils.sharedPrefernces.SharedPrefences

open class BaseFragments : Fragment() {

    companion object {
        private const val INITIAL_KEY = "android.provider.extra.INITIAL_URI"
        private const val ADVANCE_KEY = "android.content.SHOW_ADVANCED"
        private const val REQUEST_CODE = 1234
    }

    private var _baseListLiveData = MutableLiveData<List<StatusDto>>()
    val baseListLiveData: LiveData<List<StatusDto>> get() = _baseListLiveData

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
            Log.e("tree", treeUri.toString())
            // make it true but due to issue i have done it false
            // the issue was regarding treeUi
            SharedPrefences.setIsFolderSelected(requireContext(), false)
            SharedPrefences.treeUriPath(requireContext(), treeUri.toString())
            getUri(treeUri)
        }
    }

    fun getUri(treeUri: Uri?) {
        if (treeUri != null) {
            Log.e("uriIn", treeUri.toString())
            val fileDoc = activity?.let {
                it.applicationContext?.let { it1 ->
                    DocumentFile.fromTreeUri(
                        it1,
                        treeUri,
                    )
                }
            }

            val baseStatusList = mutableListOf<StatusDto>() // Create an empty list

            for (file: DocumentFile in fileDoc!!.listFiles()) {
                if (!file.name!!.endsWith(".nomedia")) {
                    val statusClass = StatusDto(file.name!!, file.uri.toString())
                    baseStatusList.add(statusClass) // Add the StatusDto object to the list
                } else {
                    Log.e("error", "some error occurred")
                }
            }

            _baseListLiveData.value =
                baseStatusList // Set the value of _baseListLiveData to the final list
        }
    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
