package com.example.eshccheck.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.eshccheck.databinding.FragmentLateUsersBinding
import com.example.eshccheck.ui.BaseFragment
import com.example.eshccheck.ui.adapters.LateFragmentAdapter
import com.example.eshccheck.ui.model.DataUi
import com.example.eshccheck.utils.LateUsersFilter
import dagger.hilt.android.AndroidEntryPoint
import org.joda.time.DateTime

@AndroidEntryPoint
class LateUsersFragment : BaseFragment<FragmentLateUsersBinding>() {

    private val vmShared by activityViewModels<MainFragmentViewModel>()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentLateUsersBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val threeDaysAgo = DateTime.now().minusDays(3).toDate().time
        binding.fragmentLateToolbar.setupWithNavController(findNavController())
        val adapter = LateFragmentAdapter()
        binding.fragmentLateRv.adapter = adapter

        vmShared.allUsers.observe(viewLifecycleOwner) { allUsersList ->
            val threeDaysLateUsersList = allUsersList.filter { it.timeLong < threeDaysAgo }
            adapter.submitList(threeDaysLateUsersList)
        }
    }
}