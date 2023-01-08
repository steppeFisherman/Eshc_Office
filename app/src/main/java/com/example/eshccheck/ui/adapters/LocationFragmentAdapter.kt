package com.example.eshccheck.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.eshccheck.R
import com.example.eshccheck.databinding.LocationItemRawBinding
import com.example.eshccheck.ui.model.DataUi

class LocationFragmentAdapter(private val listener: Listener) :
    ListAdapter<DataUi, LocationFragmentAdapter.MainHolder>(ItemCallback), View.OnClickListener {

    override fun onClick(v: View) {
        val user = v.tag as DataUi
        when (v.id) {
            R.id.txt_phone_location -> listener.dial(user)
            R.id.btn_location_location -> listener.toLocation(user)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val view = LocationItemRawBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        view.txtPhoneLocation.setOnClickListener(this)
        view.btnLocationLocation.setOnClickListener(this)

        return MainHolder(view)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val user = getItem(position)
        holder.binding.apply {
            root.tag = user
            txtPhoneLocation.tag = user
            btnLocationLocation.tag = user

            txtIdLocation.animation = AnimationUtils
                .loadAnimation(holder.binding.root.context, R.anim.fade_transition_animation)
            txtIdLocation.text = user.id
            txtTimeLocation.text = user.time
            txtNameLocation.text = user.fullName
            txtPhoneLocation.text = user.phoneUser
            txtAddressLocation.text = user.locationAddress
        }
    }

    class MainHolder(val binding: LocationItemRawBinding) : RecyclerView.ViewHolder(binding.root)

    interface Listener {
        fun dial(user: DataUi)
        fun toLocation(user: DataUi)
    }

    object ItemCallback : DiffUtil.ItemCallback<DataUi>() {
        override fun areItemsTheSame(oldItem: DataUi, newItem: DataUi) =
            oldItem.time == newItem.time

        override fun areContentsTheSame(oldItem: DataUi, newItem: DataUi) = oldItem == newItem
    }
}