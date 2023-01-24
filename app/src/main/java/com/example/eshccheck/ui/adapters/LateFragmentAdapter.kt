package com.example.eshccheck.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.eshccheck.R
import com.example.eshccheck.databinding.LateUserRvItemRawBinding
import com.example.eshccheck.ui.model.DataUi

class LateFragmentAdapter : ListAdapter<DataUi, LateFragmentAdapter.MainHolder>(ItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val view = LateUserRvItemRawBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(view)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val user = getItem(position)

        holder.binding.apply {
            txtIdLate.animation = AnimationUtils
                .loadAnimation(holder.binding.root.context, R.anim.fade_transition_animation)
            txtTimeLate.text = user.time
            txtNameLate.text = user.fullName
            txtAddressLate.text = user.locationAddress
            txtIdLate.text = user.id
            txtPhoneLate.text = user.phoneUser
        }
    }

    class MainHolder(val binding: LateUserRvItemRawBinding) : RecyclerView.ViewHolder(binding.root)

    object ItemCallback : DiffUtil.ItemCallback<DataUi>() {
        override fun areItemsTheSame(oldItem: DataUi, newItem: DataUi) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: DataUi, newItem: DataUi) = oldItem == newItem
    }
}