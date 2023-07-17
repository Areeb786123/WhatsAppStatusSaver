package com.areeb.whatsappstatussaver.ui.DetailScreen.fragments

import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.areeb.whatsappstatussaver.databinding.FragmentDetailBinding
import com.areeb.whatsappstatussaver.ui.DetailScreen.viewModels.DetailViewModels
import com.areeb.whatsappstatussaver.ui.base.fragments.BaseFragments
import com.areeb.whatsappstatussaver.utils.constants.Constants.TARGET_DIRECTORY.SHARING.Companion.FRAGMENT_IMAGE_URI
import com.areeb.whatsappstatussaver.utils.constants.Constants.TARGET_DIRECTORY.SHARING.Companion.SCREEN
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

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
        setUpScreen(arguments)
        onItemClick()
    }

    private fun setUpScreen(arguments: Bundle?) {
        val screenType = arguments?.getInt(SCREEN)
        if (screenType == 2) {
            binding.groupPhotos.visibility = View.GONE
            binding.groupVideos.visibility = View.VISIBLE
        } else {
            binding.groupPhotos.visibility = View.VISIBLE
            binding.groupVideos.visibility = View.GONE
        }
        screenType?.let { getData(arguments, it) }
    }

    private fun getData(arguments: Bundle?, screenType: Int) {
        imageUrl = arguments?.getString(FRAGMENT_IMAGE_URI).toString()
        if (screenType == 2) {
            binding.videoView.apply {
                setVideoURI(android.net.Uri.parse(imageUrl))
                binding.videoView.setOnPreparedListener {
                    // Start playing the video once it's prepared
                    binding.videoView.start()
                }
            }
        } else {
            Glide.with(requireContext()).load(imageUrl).into(binding.detailImage)
        }
    }

    private fun onItemClick() {
        binding.btnDownloadImageStatus.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            binding.btnDownloadImageStatus.id -> {
                // Request external storage permission if not granted
                lifecycleScope.launch {
                    if (arguments?.getInt(SCREEN) == 1) {
                        downloadImage()
                    } else {
                        downloadVideo(
                            Uri.parse(
                                arguments?.getString(FRAGMENT_IMAGE_URI).toString(),
                            ),
                        )
                    }
                }
            }
        }
    }

    private fun downloadImage() {
        val drawable = binding.detailImage.drawable as? BitmapDrawable
        val bitmap = drawable?.bitmap

        if (bitmap != null) {
            viewModels.saveImage(bitmap, requireActivity())
        }
    }

    private fun downloadVideo(videoUri: Uri) {
        lifecycleScope.launch(Dispatchers.IO) {
            val inputStream: InputStream? =
                requireActivity().contentResolver.openInputStream(videoUri)
            val filename = "my_video.mp4" // Specify the desired filename with the correct extension
            val outputFile =
                File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_MOVIES), filename)

            try {
                val outputStream: OutputStream = FileOutputStream(outputFile)

                inputStream?.let {
                    val buffer = ByteArray(4096)
                    var bytesRead: Int
                    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                        outputStream.write(buffer, 0, bytesRead)
                    }
                    outputStream.flush()

                    // Download successful, show a Toast message
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            requireContext(),
                            "Video downloaded successfully!",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                // Show a Toast message for any download errors
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "Failed to download video.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            } finally {
                inputStream?.close()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.videoView.setOnPreparedListener {
            // Start playing the video once it's prepared
            binding.videoView.stopPlayback()
        }
    }
}
