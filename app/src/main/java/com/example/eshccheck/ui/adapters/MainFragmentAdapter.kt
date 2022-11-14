package com.example.eshccheck.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.eshccheck.R
import com.example.eshccheck.databinding.MainRvItemRawBinding
import com.example.eshccheck.ui.model.DataUi


class MainFragmentAdapter(private val listener: Listener) :
    ListAdapter<DataUi, MainFragmentAdapter.MainHolder>(ItemCallback), View.OnClickListener {

    override fun onClick(v: View) {
        val user = v.tag as DataUi
        when (v.id) {
            R.id.txt_phone -> listener.dial(user)
            R.id.container -> listener.chooseUser(user)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val view = MainRvItemRawBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        view.txtPhone.setOnClickListener(this)
        view.container.setOnClickListener(this)

        return MainHolder(view)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val user = getItem(position)

        holder.binding.apply {
            root.tag = user
            txtPhone.tag = user
            container.tag = user

            txtId.animation = AnimationUtils
                .loadAnimation(holder.binding.root.context, R.anim.fade_transition_animation)
            txtName.text = user.full_name
            txtId.text = user.id.toString()
            txtPhone.text = user.phone_user
        }
    }

    class MainHolder(val binding: MainRvItemRawBinding) : RecyclerView.ViewHolder(binding.root)

    interface Listener {
        fun chooseUser(user: DataUi)
        fun dial(user: DataUi)
    }

    object ItemCallback : DiffUtil.ItemCallback<DataUi>() {
        override fun areItemsTheSame(oldItem: DataUi, newItem: DataUi): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataUi, newItem: DataUi): Boolean {
            return oldItem == newItem
        }
    }
}