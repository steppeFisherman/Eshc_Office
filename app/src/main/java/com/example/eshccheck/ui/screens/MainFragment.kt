package com.example.eshccheck.ui.screens

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.eshccheck.R
import com.example.eshccheck.data.model.cloudModel.DataCloud
import com.example.eshccheck.databinding.FragmentMainBinding
import com.example.eshccheck.ui.BaseFragment
import com.example.eshccheck.ui.adapters.MainFragmentAdapter
import com.example.eshccheck.ui.model.DataUi
import com.example.eshccheck.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>() {

    private val vm by viewModels<MainFragmentViewModel>()
    private lateinit var player: MediaPlayer

    override fun onAttach(context: Context) {
        super.onAttach(context)
        player = MediaPlayer.create(context, R.raw.alarm)
    }

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMainBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MainFragmentAdapter(object : MainFragmentAdapter.Listener {

            override fun chooseUser(user: DataUi) {
                showToast(view.context, user.fullName)
            }

            override fun dial(user: DataUi) {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:${user.phoneOperator}")
                ContextCompat.startActivity(view.context, intent, null)
            }
        })
        binding.rvFragmentMain.adapter = adapter

        vm.users.observe(viewLifecycleOwner) { listDataUi ->
            adapter.submitList(listDataUi)
        }

//        vm.usersAlarmed.observe(viewLifecycleOwner) { usersAlarmed ->
//            usersAlarmed.forEach {
//                when (it.alarm) {
//                    true -> {
//                        player.start()
//                    }
//                    false -> {
//                        player.seekTo(0)
//                        player.pause()
//                    }
//                }
//            }
//        }

        vm.error.observe(viewLifecycleOwner) { errorType ->
            when (errorType.ordinal) {
                0 -> view.snackLong(R.string.no_connection_exception_message)
                1 -> view.snackLong(R.string.database_exception_message)
                2 -> view.snackLong(R.string.http_exception_message)
                3 -> view.snackLong(R.string.user_not_registered_exception_message)
                4 -> view.snackLong(R.string.database_exception_message)
                5 -> view.snackLong(R.string.generic_exception_message)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        REF_DATABASE_ROOT.child(NODE_USERS)
            .addChildEventListener(SnapShotChildListener { dataSnapshot ->
                if (dataSnapshot.exists()) {
                    val dataCloud = dataSnapshot.getValue(DataCloud::class.java) ?: DataCloud()

                    when (dataCloud.alarm) {
                        true -> {
                            player.start()
                            val bundle = bundleOf("dataCloud" to dataCloud)
                            findNavController().navigate(R.id.alarmFragment, bundle)
                        }
                        false -> {
                            player.seekTo(0)
                            player.pause()
                            findNavController().popBackStack()
                        }
                    }
                }
            })
    }

    interface PermissionHandle {
        fun check()
    }
}