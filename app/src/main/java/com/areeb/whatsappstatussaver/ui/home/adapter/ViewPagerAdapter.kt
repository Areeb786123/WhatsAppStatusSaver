package com.areeb.whatsappstatussaver.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.areeb.whatsappstatussaver.ui.home.fragments.PhotosFragments
import com.areeb.whatsappstatussaver.ui.home.fragments.VideoFragments

class ViewPagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> PhotosFragments()
            1 -> VideoFragments()
            else -> PhotosFragments()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Photos"
            1 -> "Videos"
            else -> "error"
        }
    }
}
