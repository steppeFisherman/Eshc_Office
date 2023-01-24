package com.example.eshccheck.ui.screens

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.eshccheck.R
import com.example.eshccheck.databinding.FragmentLocationBinding
import com.example.eshccheck.map.MapsActivity
import com.example.eshccheck.ui.BaseFragment
import com.example.eshccheck.ui.adapters.LocationFragmentAdapter
import com.example.eshccheck.ui.model.DataUi
import com.example.eshccheck.utils.snackLong
import com.example.eshccheck.utils.snowSnackIndefiniteTop
import com.example.eshccheck.utils.visible
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationFragment : BaseFragment<FragmentLocationBinding>() {

    private val vm by viewModels<LocationFragmentViewModel>()
    private lateinit var snack: Snackbar

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentLocationBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentLocationToolbar.setupWithNavController(findNavController())
        snack = Snackbar.make(view, "", Snackbar.LENGTH_INDEFINITE)

        val adapter = LocationFragmentAdapter(object : LocationFragmentAdapter.Listener {

            override fun dial(user: DataUi) {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:${user.phoneOperator}")
                ContextCompat.startActivity(requireContext(), intent, null)
            }

            override fun toLocation(user: DataUi) {
                val intent = Intent(requireContext(), MapsActivity::class.java)
                intent.putExtra("user", user)
                startActivity(intent)
            }
        })

        binding.locationFragmentRv.adapter = adapter

        vm.users.observe(viewLifecycleOwner) { listDataUi ->
            if (listDataUi.isNullOrEmpty()) binding.progressBarLocation.visible(true)
            else {
                adapter.submitList(listDataUi.asReversed())
                binding.progressBarLocation.visible(false)
            }
        }

        vm.error.observe(viewLifecycleOwner) {
            when (it.ordinal) {
                0 -> view.snackLong(R.string.no_connection_exception_message)
                1 -> view.snackLong(R.string.database_exception_message)
                2 -> view.snackLong(R.string.http_exception_message)
                3 -> view.snackLong(R.string.user_not_registered_exception_message)
                4 -> view.snowSnackIndefiniteTop(snack, R.string.database_exception_message)
                5 -> view.snackLong(R.string.generic_exception_message)
            }
        }
    }
}