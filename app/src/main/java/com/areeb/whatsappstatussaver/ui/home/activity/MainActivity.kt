package com.areeb.whatsappstatussaver.ui.home.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.areeb.whatsappstatussaver.R
import com.areeb.whatsappstatussaver.databinding.ActivityMainBinding
import com.areeb.whatsappstatussaver.ui.home.adapter.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var viewPagerAdapter: ViewPagerAdapter? = null

    companion object {
        fun startMainActivity(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)
        init()
    }

    private fun init() {
        settingUpToolBar()
        viewPagerSetup()
    }

    private fun viewPagerSetup() {
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        // Set the adapter to the ViewPager
        binding.viewPager.adapter = viewPagerAdapter

        // Connect the TabLayout with the ViewPager
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    private fun settingUpToolBar() {
        binding.toolBarTitle.text = getString(R.string.status_saver)
    }


}
