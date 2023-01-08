package com.example.eshccheck.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.eshccheck.R
import com.example.eshccheck.databinding.CommentItemRawBinding
import com.example.eshccheck.ui.model.DataUi

class AlarmFragmentAdapter(private val listener: Listener) :
    ListAdapter<DataUi, AlarmFragmentAdapter.MainHolder>(ItemCallback), View.OnClickListener {

    override fun onClick(v: View) {
        val user = v.tag as DataUi
        when (v.id) {
            R.id.txt_phone_comment -> listener.dial(user)
            R.id.btn_location_comment -> listener.toLocation(user)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val view = CommentItemRawBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        view.txtPhoneComment.setOnClickListener(this)
        view.btnLocationComment.setOnClickListener(this)

        return MainHolder(view)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val user = getItem(position)

        holder.binding.apply {
            root.tag = user
            txtPhoneComment.tag = user
            btnLocationComment.tag = user
            val comment = holder.itemView.context
                .getString(R.string.comments_template, user.comment)

            txtIdComment.animation = AnimationUtils
                .loadAnimation(holder.binding.root.context, R.anim.fade_transition_animation)
            txtIdComment.text = user.id
            txtTimeComment.text = user.time
            txtNameComment.text = user.fullName
            txtPhoneComment.text = user.phoneUser
            txtComment.text = comment
            txtLocationAddressComment.text = user.locationAddress
        }
    }

    class MainHolder(val binding: CommentItemRawBinding) : RecyclerView.ViewHolder(binding.root)

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