package com.areeb.whatsappstatussaver.ui.DetailScreen.fragments

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.areeb.whatsappstatussaver.databinding.FragmentDetailBinding
import com.areeb.whatsappstatussaver.ui.base.fragments.BaseFragments
import com.areeb.whatsappstatussaver.utils.constants.Constants.TARGET_DIRECTORY.SHARING.Companion.FRAGMENT_IMAGE_URI
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.IOException

@AndroidEntryPoint
class DetailFragment : BaseFragments(), View.OnClickListener {

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
                lifecycleScope.launch{
                    downloadImage()
                }
            }
        }
    }

    private suspend fun downloadImage() {
        val drawable = binding.detailImage.drawable as? BitmapDrawable
        val bitmap = drawable?.bitmap

        if (bitmap != null) {
            saveImage(bitmap)
        }
    }

    private suspend fun saveImage(bitmap: Bitmap) {
        val contentResolver: ContentResolver = requireActivity().applicationContext.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "image.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        }

        var imageUri: String? = null

        try {
            val imageCollection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } else {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }

            val imageUri = contentResolver.insert(imageCollection, contentValues)
            if (imageUri != null) {
                contentResolver.openOutputStream(imageUri)?.use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                }

                Toast.makeText(
                    requireContext(),
                    "Image saved successfully.",
                    Toast.LENGTH_SHORT,
                ).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Failed to save the image.",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            imageUri?.let { uri ->
                contentResolver.delete(Uri.parse(uri), null, null)
            }
        }
    }
}
