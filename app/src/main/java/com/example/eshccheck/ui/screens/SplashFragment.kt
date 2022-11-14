package com.example.eshccheck.ui.screens

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eshccheck.data.repository.ToDispatch
import com.example.eshccheck.databinding.FragmentSplashBinding
import com.example.eshccheck.ui.BaseFragment

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

class SplashFragment() : BaseFragment<FragmentSplashBinding>() {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }
    private val scope = CoroutineScope(Job() + exceptionHandler)
    private val dispatchers: ToDispatch = ToDispatch.Base()
    private var firstTimeUser = false
    private lateinit var preferences: SharedPreferences

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentSplashBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}