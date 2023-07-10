package com.areeb.whatsappstatussaver.ui.DetailScreen.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.areeb.whatsappstatussaver.databinding.FragmentDetailBinding
import com.areeb.whatsappstatussaver.utils.constants.Constants.TARGET_DIRECTORY.SHARING.Companion.FRAGMENT_IMAGE_URI
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

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
    }

    private fun getData(arguments: Bundle?) {
        Log.e("aa", arguments?.getString(FRAGMENT_IMAGE_URI).toString())
        imageUrl = arguments?.getString(FRAGMENT_IMAGE_URI).toString()
        Glide.with(requireContext()).load(imageUrl).into(binding.detailImage)
    }
}
