package com.a7medkenawy.elmarket.ui.activities.onboardingscreen.onboardingfragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.a7medkenawy.elmarket.R
import com.a7medkenawy.elmarket.databinding.FragmentSecondScreenBinding
import com.a7medkenawy.elmarket.databinding.FragmentThirdScreenBinding
import com.a7medkenawy.elmarket.ui.activities.DashBoardActivity
import com.a7medkenawy.elmarket.ui.activities.LoginActivity

class ThirdScreen : Fragment() {

    lateinit var binding: FragmentThirdScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThirdScreenBinding.inflate(inflater, container, false)


        binding.onBoardingThirdScreen.setOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            finishOnBoarding()
            requireActivity().finish()
        }
        return binding.root
    }


    private fun finishOnBoarding() {
        val sharedPreferences =
            requireActivity().getSharedPreferences("OnBoardingScreen", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("onBoarding_Key", true).apply()
    }


}