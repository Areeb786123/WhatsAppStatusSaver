package com.areeb.whatsappstatussaver.ui.DetailScreen.fragments

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.areeb.whatsappstatussaver.databinding.FragmentDetailBinding
import com.areeb.whatsappstatussaver.ui.base.fragments.BaseFragments
import com.areeb.whatsappstatussaver.utils.constants.Constants.TARGET_DIRECTORY.SHARING.Companion.FRAGMENT_IMAGE_URI
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

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

    private fun downloadImage() {
        val drawable = binding.detailImage.drawable as? BitmapDrawable
        val bitmap = drawable?.bitmap

        if (bitmap != null) {
            saveImageToExternalStorage(bitmap)
        }
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        PERMISSION_REQUEST_CODE,
                    )
                }
            }
        }
    }

    private fun saveImageToExternalStorage(bitmap: Bitmap) {
        val fileName = "image.jpg"
        val directory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)

        val file = File(directory, fileName)

        try {
            FileOutputStream(file).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.flush()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun saveBitmapFile() {
        val drawable = binding.detailImage.drawable as? BitmapDrawable
        val bitmap = drawable?.bitmap

        val displayName = "my_image.png" // Name of the image file
        val mimeType = "image/png" // MIME type of the image file

        val imageCollection =
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        val imageDetails = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, displayName)
            put(MediaStore.Images.Media.MIME_TYPE, mimeType)
        }

        val resolver = requireContext().contentResolver
        var outputStream: OutputStream? = null

        try {
            val imageUri = resolver.insert(imageCollection, imageDetails)
            outputStream = imageUri?.let { resolver.openOutputStream(it) }
            bitmap?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            Toast.makeText(requireContext(), "Image saved successfully", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Failed to save image", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        } finally {
            outputStream?.close()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            saveBitmapFile()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
