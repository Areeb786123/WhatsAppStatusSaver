package com.areeb.whatsappstatussaver.ui.home.fragments

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.areeb.whatsappstatussaver.data.models.StatusDto
import com.areeb.whatsappstatussaver.databinding.FragmentPhotosFragmentsBinding
import com.areeb.whatsappstatussaver.ui.base.fragments.BaseFragments
import com.areeb.whatsappstatussaver.utils.sharedPrefernces.SharedPrefences
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotosFragments : BaseFragments() {
    private var _binding: FragmentPhotosFragmentsBinding? = null
    private val binding get() = _binding
    private var statusList: List<StatusDto>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPhotosFragmentsBinding.inflate(layoutInflater)
        return _binding!!.root
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!SharedPrefences.isFolderSelected(requireContext())) {
            getFolderPermission()
        } else {
            getUri(Uri.parse(SharedPrefences.getTreeUriPath(requireContext())))
            showToast("permission granted")
        }
    }
}
