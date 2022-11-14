package com.example.eshccheck.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.eshccheck.R
import com.example.eshccheck.databinding.MainRvItemRawBinding
import com.example.eshccheck.ui.model.DataUi

class MainFragmentAdapter :
    RecyclerView.Adapter<MainFragmentAdapter.MainHolder>() {

    private var mList = emptyList<DataUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val view = MainRvItemRawBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(view)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.binding.apply {
            txtId.animation = AnimationUtils
                .loadAnimation(holder.binding.root.context, R.anim.fade_transition_animation)
            txtName.text = mList[position].full_name
            txtId.text = mList[position].id.toString()
            txtPhone.text = mList[position].phone_user
        }
    }

    override fun getItemCount() = mList.size

    class MainHolder(val binding: MainRvItemRawBinding) : RecyclerView.ViewHolder(binding.root)

    fun setData(newList: List<DataUi>) {
        val diffUtil = MainFragmentDiffUtil(mList, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        mList = newList
        diffResult.dispatchUpdatesTo(this)
    }
}