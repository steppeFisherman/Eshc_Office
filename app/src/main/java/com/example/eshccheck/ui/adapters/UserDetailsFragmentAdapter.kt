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
import com.example.eshccheck.databinding.HistoryCommentItemRawBinding
import com.example.eshccheck.databinding.HistoryItemRawBinding
import com.example.eshccheck.ui.model.DataUi
import com.example.eshccheck.utils.visible


class UserDetailsFragmentAdapter(private val listener: Listener) :
    ListAdapter<DataUi, UserDetailsFragmentAdapter.RecyclerViewHolder>(ItemCallback),
    View.OnClickListener {

    override fun onClick(v: View) {
        val user = v.tag as DataUi
        when (v.id) {
            R.id.btn_location -> listener.toLocation(user)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {

        val simpleBinding = HistoryItemRawBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        simpleBinding.btnLocation.setOnClickListener(this)

        val commentBinding = HistoryCommentItemRawBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        commentBinding.btnLocation.setOnClickListener(this)

        return when (viewType) {
            TYPE_SIMPLE -> RecyclerViewHolder.MainHolder(simpleBinding)
            else -> RecyclerViewHolder.CommentHolder(commentBinding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val user = getItem(position)

        when (holder) {
            is RecyclerViewHolder.MainHolder -> {
                holder.binding.apply {
                    root.tag = user
                    btnLocation.tag = user

                    txtId.animation = AnimationUtils
                        .loadAnimation(
                            holder.binding.root.context,
                            R.anim.fade_transition_animation
                        )
                    txtId.text = user.id
                    txtTime.text = user.time
                    txtName.text = user.fullName
                    txtPhone.text = user.phoneUser
                    txtLocationAddress.text = user.locationAddress
                    if (user.locationFlagOnly) imgAlarm.visible(false) else imgAlarm.visible(true)
                }
            }
            is RecyclerViewHolder.CommentHolder -> {
                holder.binding.apply {
                    root.tag = user
                    btnLocation.tag = user
                    val comment = holder.itemView.context
                        .getString(R.string.comments_template, user.comment)

                    txtId.animation = AnimationUtils
                        .loadAnimation(
                            holder.binding.root.context,
                            R.anim.fade_transition_animation
                        )
                    txtId.text = user.id
                    txtTime.text = user.time
                    txtName.text = user.fullName
                    txtPhone.text = user.phoneUser
                    txtLocationAddress.text = user.locationAddress
                    txtComment.text = comment
                    if (user.locationFlagOnly) imgAlarm.visible(false) else imgAlarm.visible(true)
                }
            }
        }
    }

    sealed class RecyclerViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

        class MainHolder(val binding: HistoryItemRawBinding) : RecyclerViewHolder(binding)

        class CommentHolder(val binding: HistoryCommentItemRawBinding) : RecyclerViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        val user = getItem(position)
        return when (user.locationFlagOnly) {
            true -> TYPE_SIMPLE
            else -> TYPE_COMMENT
        }
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

    companion object {
        private const val TYPE_SIMPLE = 0
        private const val TYPE_COMMENT = 1
    }
}