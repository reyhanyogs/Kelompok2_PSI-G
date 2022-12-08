package com.example.tugasbesarpsi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tugasbesarpsi.adapter.SectionPagerAdapter
import com.example.tugasbesarpsi.databinding.ActivityRegisterLoginBinding
import com.google.android.material.tabs.TabLayoutMediator

class RegisterLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionPagerAdapter = SectionPagerAdapter(this)
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        if (STATUS == 0) {
            binding.tabs.getTabAt(0)?.select()
        } else {
            binding.tabs.getTabAt(1)?.select()
        }
    }

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.register,
            R.string.login
        )
        var STATUS = 0
    }
}