package com.areeb.whatsappstatussaver.ui.home.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.areeb.whatsappstatussaver.data.models.StatusDto
import com.areeb.whatsappstatussaver.databinding.FragmentVideoFragmentsBinding
import com.areeb.whatsappstatussaver.ui.base.fragments.BaseFragments
import com.areeb.whatsappstatussaver.ui.common.OnItemClick
import com.areeb.whatsappstatussaver.ui.home.adapter.ImageStatusAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoFragments : BaseFragments() {

    private var _binding: FragmentVideoFragmentsBinding? = null
    private val binding get() = _binding!!
    private lateinit var videoStatusAdapter: ImageStatusAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentVideoFragmentsBinding.inflate(layoutInflater)
        return _binding!!.root
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun init() {
        videoStatusAdapter = ImageStatusAdapter(emptyList(), onItemClick, true)
        binding.recyclerViewVideo.adapter = videoStatusAdapter
        getStatusAccess()
        observer()
    }

    private fun observer() {
        baseListLiveData.observe(viewLifecycleOwner) { videoUri ->
            setRecyclerView(videoUri.filter { it.fileUri.endsWith("mp4") || it.fileUri.endsWith("Mp4") })
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setRecyclerView(value: List<StatusDto>) {
        Log.e("ss", value.toString())
        binding.let {
            videoStatusAdapter.submitList(value)
            videoStatusAdapter.notifyDataSetChanged()
        }
    }

    private val onItemClick = object : OnItemClick {
        override fun onItemClick(statusDto: StatusDto) {
            showToast("click")
        }
    }
}
