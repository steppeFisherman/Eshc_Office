package com.example.eshccheck.utils.listeners

import androidx.appcompat.widget.SearchView

typealias SearchListener = (text: String) -> Unit

class SearchViewListener(private val listener: SearchListener) : SearchView.OnQueryTextListener{
    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        listener(newText)
        return true
    }
}