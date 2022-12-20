package com.example.eshccheck.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.eshccheck.R
import com.example.eshccheck.databinding.HistoryCommentItemRawBinding
import com.example.eshccheck.databinding.HistoryItemRawBinding
import com.example.eshccheck.ui.model.DataUi
import com.example.eshccheck.utils.visible

class AlarmFragmentAdapter(private val listener: Listener) :
    ListAdapter<DataUi, AlarmFragmentAdapter.MainHolder>(ItemCallback), View.OnClickListener {

    override fun onClick(v: View) {
        val user = v.tag as DataUi
        when (v.id) {
            R.id.btn_location -> listener.toLocation(user)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val view = HistoryCommentItemRawBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        view.btnLocation.setOnClickListener(this)
        return MainHolder(view)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val user = getItem(position)

        holder.binding.apply {
            root.tag = user
            btnLocation.tag = user
            val comment = holder.itemView.context
                .getString(R.string.comments_template, user.comment)

            txtId.animation = AnimationUtils
                .loadAnimation(holder.binding.root.context, R.anim.fade_transition_animation)
            txtId.text = user.id
            txtTime.text = user.time
            txtName.text = user.fullName
            txtPhone.text = user.phoneUser
            txtComment.text = comment
            txtLocationAddress.text = user.locationAddress
        }
    }

    class MainHolder(val binding: HistoryCommentItemRawBinding) : RecyclerView.ViewHolder(binding.root) {
        val imgAlarm = binding.imgAlarm.visible(true)
    }

    interface Listener {
        fun toLocation(user: DataUi)
    }

    object ItemCallback : DiffUtil.ItemCallback<DataUi>() {
        override fun areItemsTheSame(oldItem: DataUi, newItem: DataUi): Boolean {
            return oldItem.time == newItem.time
        }

        override fun areContentsTheSame(oldItem: DataUi, newItem: DataUi): Boolean {
            return oldItem == newItem
        }
    }
}