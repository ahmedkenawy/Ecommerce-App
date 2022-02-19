package com.a7medkenawy.elmarket.ui.activities.onboardingscreen.onboardingfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.a7medkenawy.elmarket.R
import com.a7medkenawy.elmarket.databinding.FragmentFirstScreenBinding
import com.a7medkenawy.elmarket.databinding.FragmentSecondScreenBinding

class SecondScreen : Fragment() {


    lateinit var binding: FragmentSecondScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondScreenBinding.inflate(inflater, container, false)


        val vp = activity?.findViewById<ViewPager2>(R.id.on_boarding_view_pager)

        binding.onBoardingSecondScreen.setOnClickListener {
            vp?.currentItem = 2
        }
        return binding.root
    }


}