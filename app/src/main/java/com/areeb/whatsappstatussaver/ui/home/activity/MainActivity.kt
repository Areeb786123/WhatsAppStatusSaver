package com.areeb.whatsappstatussaver.ui.home.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.storage.StorageManager
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.documentfile.provider.DocumentFile
import com.areeb.whatsappstatussaver.R
import com.areeb.whatsappstatussaver.data.models.StatusDto
import com.areeb.whatsappstatussaver.databinding.ActivityMainBinding
import com.areeb.whatsappstatussaver.ui.base.fragments.BaseFragments
import com.areeb.whatsappstatussaver.ui.home.adapter.ViewPagerAdapter
import com.areeb.whatsappstatussaver.utils.constants.Constants
import com.areeb.whatsappstatussaver.utils.sharedPrefernces.SharedPrefences
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

        private const val INITIAL_KEY = "android.provider.extra.INITIAL_URI"
        private const val ADVANCE_KEY = "android.content.SHOW_ADVANCED"
        private const val REQUEST_CODE = 1234
    }

    fun getUri(treeUri: Uri?) {
        if (treeUri != null) {
            Log.e("uriIn", treeUri.toString())
            val fileDoc = DocumentFile.fromTreeUri(this, treeUri)

            val baseStatusList = mutableListOf<StatusDto>() // Create an empty list

            for (file: DocumentFile in fileDoc!!.listFiles()) {
                if (!file.name!!.endsWith(".nomedia")) {
                    val statusClass = StatusDto(file.name!!, file.uri.toString())
                    baseStatusList.add(statusClass) // Add the StatusDto object to the list
                } else {
                    Log.e("error", "some error occurred")
                }
            }

            BaseFragments.getUrlFromUrl(baseStatusList)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val treeUri = data?.data
            Log.e("tree", treeUri.toString())
            // make it true but due to issue i have done it false
            // the issue was regarding treeUi
            SharedPrefences.setIsFolderSelected(this, false)
            SharedPrefences.treeUriPath(this, treeUri.toString())
            getUri(treeUri)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun getStatusAccess() {
        getFolderPermission()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun getFolderPermission() {
        val storageManager =
            getSystemService(Context.STORAGE_SERVICE) as StorageManager
        val intent = storageManager.primaryStorageVolume.createOpenDocumentTreeIntent()
        val statusUri = Uri.parse(Constants.TARGET_DIRECTORY.TARGET_DIRECTORY_KEY)
        intent.putExtra(INITIAL_KEY, statusUri)
        intent.putExtra(ADVANCE_KEY, true)
        startActivityForResult(intent, REQUEST_CODE)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)
        init()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun init() {
        getStatusAccess()
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
