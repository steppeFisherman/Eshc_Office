package com.example.eshccheck.ui.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.eshccheck.databinding.FragmentSplashBinding
import com.example.eshccheck.ui.BaseFragment

class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentSplashBinding.inflate(inflater, container, false)
}