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
import com.areeb.whatsappstatussaver.utils.constants.Constants.TARGET_DIRECTORY.SHARING.Companion.SCREEN
import com.areeb.whatsappstatussaver.utils.constants.Constants.TARGET_DIRECTORY.SHARING.Companion.SCREEN_TYPE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    companion object {

        // Screen type 1 -> photo fragment
        // Screen type 2-> vide0 fragment
        fun startDetailFragment(context: Context, imageUri: String, screenType: Int) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(IMAGE_URI, imageUri)
            intent.putExtra(SCREEN_TYPE, screenType)
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
            putInt(SCREEN, intent.getIntExtra(SCREEN_TYPE, 1))
        }
        val fragment = DetailFragment()
        fragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .add(binding.fragmentContainerView.id, fragment)
            .commit()
    }
}
