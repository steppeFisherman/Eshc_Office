package com.example.eshccheck.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.eshccheck.ui.model.DataUi

class MainFragmentDiffUtil(
    private val oldList: List<DataUi>,
    private val newList: List<DataUi>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}