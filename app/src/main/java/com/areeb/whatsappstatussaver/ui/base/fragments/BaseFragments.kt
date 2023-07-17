package com.areeb.whatsappstatussaver.ui.base.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.areeb.whatsappstatussaver.data.models.StatusDto

open class BaseFragments : Fragment() {

    companion object {

        const val PERMISSION_REQUEST_CODE = 123
        private var _baseListLiveData = MutableLiveData<List<StatusDto>>()
        val baseListLiveData: LiveData<List<StatusDto>> get() = _baseListLiveData
        fun getUrlFromUrl(baseStatusList: MutableList<StatusDto>) {
            _baseListLiveData.value = baseStatusList
        }
    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun checkRequestPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
        return true
    }

    fun requestExternalPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE,
        )
    }
}
