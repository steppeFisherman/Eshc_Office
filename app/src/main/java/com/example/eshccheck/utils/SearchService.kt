package com.example.eshccheck.utils

import android.util.Log
import android.widget.Filter
import android.widget.Filterable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.eshccheck.ui.model.DataUi

interface SearchService {

    fun result(list: List<DataUi>, newText: String)

    class Base : Filterable, SearchService {

        private val formatUiPhoneNumber = FormatUiPhoneNumber.FromUi()
        private var mList = mutableListOf<DataUi>()
        private var mListFiltered = mutableListOf<DataUi>()

        private var mListLivaData = MutableLiveData<List<DataUi>>()
        val mListFilteredLiveData: LiveData<List<DataUi>>
            get() = mListLivaData


        override fun getFilter(): Filter {
            return object : Filter() {
                override fun performFiltering(charSequence: CharSequence?): FilterResults {
                    val key = charSequence.toString().lowercase().trim()
//                    Log.d("BB", "performFiltering key: $key")
                    mListFiltered = if (key.isEmpty()) mList
                    else {

                        val newList = mutableListOf<DataUi>()
                        for (user in mList) {
                            val name = user.fullName.lowercase().trim()
                            val phone = formatUiPhoneNumber.modify(user.phoneUser.trim())
                            if (name.contains(key) || phone.contains(key)) newList.add(user)
                        }
                        newList
                    }
                    val filterResults = FilterResults()
                    filterResults.values = mListFiltered
                    filterResults.count = mListFiltered.size
                    return filterResults
                }

                override fun publishResults(
                    charSequence: CharSequence?,
                    filterResults: FilterResults?
                ) {
                    mListFiltered = filterResults?.values as MutableList<DataUi>
//                    Log.d("BB", "publishResults: ${mListFiltered.size}")
                    mListLivaData.value = mListFiltered
                }
            }
        }

        override fun result(list: List<DataUi>, newText: String) {
            mList = list.toMutableList()
            filter.filter(newText)
        }
    }
}