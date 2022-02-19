package com.a7medkenawy.elmarket.ui.activities.onboardingscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.a7medkenawy.elmarket.databinding.ActivityOnBoardingScreenBinding
import com.a7medkenawy.elmarket.ui.activities.onboardingscreen.onboardingfragments.FirstScreen
import com.a7medkenawy.elmarket.ui.activities.onboardingscreen.onboardingfragments.SecondScreen
import com.a7medkenawy.elmarket.ui.activities.onboardingscreen.onboardingfragments.ThirdScreen
import java.util.ArrayList

class OnBoardingScreen : AppCompatActivity() {

    lateinit var binding: ActivityOnBoardingScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingScreenBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)
        val fragments = ArrayList<Fragment>()
        fragments.add(FirstScreen())
        fragments.add(SecondScreen())
        fragments.add(ThirdScreen())

        val pagerAdapter = OnBoardingPagerAdapter(fragments, supportFragmentManager, lifecycle)

        binding.onBoardingViewPager.adapter = pagerAdapter

    }
}