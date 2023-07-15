package com.areeb.whatsappstatussaver.ui.DetailScreen.fragments

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.areeb.whatsappstatussaver.databinding.FragmentDetailBinding
import com.areeb.whatsappstatussaver.ui.DetailScreen.viewModels.DetailViewModels
import com.areeb.whatsappstatussaver.ui.base.fragments.BaseFragments
import com.areeb.whatsappstatussaver.utils.constants.Constants.TARGET_DIRECTORY.SHARING.Companion.FRAGMENT_IMAGE_URI
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : BaseFragments(), View.OnClickListener {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModels: DetailViewModels by viewModels()

    private var imageUrl: String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(layoutInflater)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData(arguments)
        onItemClick()
    }

    private fun getData(arguments: Bundle?) {
        imageUrl = arguments?.getString(FRAGMENT_IMAGE_URI).toString()
        Glide.with(requireContext()).load(imageUrl).into(binding.detailImage)
    }

    private fun onItemClick() {
        binding.btnDownloadImageStatus.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            binding.btnDownloadImageStatus.id -> {
                // Request external storage permission if not granted
                lifecycleScope.launch {
                    downloadImage()
                }
            }
        }
    }

    private suspend fun downloadImage() {
        val drawable = binding.detailImage.drawable as? BitmapDrawable
        val bitmap = drawable?.bitmap

        if (bitmap != null) {
            viewModels.saveImage(bitmap, requireActivity())
        }
    }
}
