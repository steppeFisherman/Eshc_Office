package com.example.eshccheck.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.eshccheck.R
import com.example.eshccheck.databinding.CommentItemRawBinding
import com.example.eshccheck.databinding.LocationItemRawBinding
import com.example.eshccheck.ui.model.DataUi


class UserDetailsFragmentAdapter(private val listener: Listener) :
    ListAdapter<DataUi, UserDetailsFragmentAdapter.RecyclerViewHolder>(ItemCallback),
    View.OnClickListener {

    override fun onClick(v: View) {
        val user = v.tag as DataUi
        when (v.id) {
            R.id.btn_location_location -> listener.toLocation(user)
            R.id.btn_location_comment -> listener.toLocation(user)
            R.id.txt_phone_location -> listener.dial(user)
            R.id.txt_phone_comment -> listener.dial(user)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {

        val simpleBinding = LocationItemRawBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        simpleBinding.btnLocationLocation.setOnClickListener(this)
        simpleBinding.txtPhoneLocation.setOnClickListener(this)

        val commentBinding = CommentItemRawBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        commentBinding.btnLocationComment.setOnClickListener(this)
        commentBinding.txtPhoneComment.setOnClickListener(this)

        return when (viewType) {
            TYPE_SIMPLE -> RecyclerViewHolder.LocationHolder(simpleBinding)
            else -> RecyclerViewHolder.CommentHolder(commentBinding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val user = getItem(position)

        when (holder) {
            is RecyclerViewHolder.LocationHolder -> {
                holder.binding.apply {
                    root.tag = user
                    btnLocationLocation.tag = user
                    txtPhoneLocation.tag = user

                    txtIdLocation.animation = AnimationUtils
                        .loadAnimation(
                            holder.binding.root.context,
                            R.anim.fade_transition_animation
                        )
                    txtIdLocation.text = user.id
                    txtTimeLocation.text = user.time
                    txtNameLocation.text = user.fullName
                    txtPhoneLocation.text = user.phoneUser
                    txtAddressLocation.text = user.locationAddress
                }
            }
            is RecyclerViewHolder.CommentHolder -> {
                holder.bindingComment.apply {
                    root.tag = user
                    btnLocationComment.tag = user
                    txtPhoneComment.tag = user
                    val comment = holder.itemView.context
                        .getString(R.string.comments_template, user.comment)

                    txtIdComment.animation = AnimationUtils
                        .loadAnimation(
                            holder.bindingComment.root.context,
                            R.anim.fade_transition_animation
                        )
                    txtIdComment.text = user.id
                    txtTimeComment.text = user.time
                    txtNameComment.text = user.fullName
                    txtPhoneComment.text = user.phoneUser
                    txtLocationAddressComment.text = user.locationAddress
                    txtComment.text = comment
                }
            }
        }
    }

    sealed class RecyclerViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        class LocationHolder(val binding: LocationItemRawBinding) : RecyclerViewHolder(binding)
        class CommentHolder(val bindingComment: CommentItemRawBinding) :
            RecyclerViewHolder(bindingComment)
    }

    override fun getItemViewType(position: Int): Int {
        val user = getItem(position)
        return when (user.locationFlagOnly) {
            true -> TYPE_SIMPLE
            else -> TYPE_COMMENT
        }
    }

    interface Listener {
        fun dial(user: DataUi)
        fun toLocation(user: DataUi)
    }

    object ItemCallback : DiffUtil.ItemCallback<DataUi>() {
        override fun areItemsTheSame(oldItem: DataUi, newItem: DataUi) =
            oldItem.time == newItem.time

        override fun areContentsTheSame(oldItem: DataUi, newItem: DataUi) = oldItem == newItem
    }

    companion object {
        private const val TYPE_SIMPLE = 0
        private const val TYPE_COMMENT = 1
    }
}