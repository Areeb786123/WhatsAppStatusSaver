package com.areeb.whatsappstatussaver.ui.DetailScreen.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.areeb.whatsappstatussaver.databinding.ActivityDetailBinding
import com.areeb.whatsappstatussaver.ui.DetailScreen.fragments.DetailFragment
import com.areeb.whatsappstatussaver.utils.constants.Constants.TARGET_DIRECTORY.SHARING.Companion.FRAGMENT_IMAGE_URI
import com.areeb.whatsappstatussaver.utils.constants.Constants.TARGET_DIRECTORY.SHARING.Companion.IMAGE_URI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun startDetailFragment(context: Context, imageUri: String) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(IMAGE_URI, imageUri)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)
        savingDataToBundle()
    }

    private fun savingDataToBundle() {
        Log.e("aa", intent.getStringExtra(IMAGE_URI).toString())
        val bundle = Bundle().apply {
            putString(FRAGMENT_IMAGE_URI, intent.getStringExtra(IMAGE_URI))
        }
        val fragment = DetailFragment()
        fragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .add(binding.fragmentContainerView.id, fragment)
            .commit()
    }
}
