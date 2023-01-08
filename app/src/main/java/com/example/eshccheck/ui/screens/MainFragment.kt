package com.example.eshccheck.ui.screens

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.eshccheck.R
import com.example.eshccheck.databinding.FragmentMainBinding
import com.example.eshccheck.ui.BaseFragment
import com.example.eshccheck.ui.MainActivity
import com.example.eshccheck.ui.adapters.MainFragmentAdapter
import com.example.eshccheck.ui.model.DataUi
import com.example.eshccheck.utils.SearchService
import com.example.eshccheck.utils.listeners.SearchViewListener
import com.example.eshccheck.utils.snackLong
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>() {

    private val vm by viewModels<MainFragmentViewModel>()
    private lateinit var mAdapter: MainFragmentAdapter
    private lateinit var mSearchView: SearchView
    private var mList = listOf<DataUi>()
    private val searchService = SearchService.Base()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMainBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        (requireActivity() as? MainActivity)?.setSupportActionBar(binding.fragmentMainToolbar)

        mAdapter = MainFragmentAdapter(object : MainFragmentAdapter.Listener {
            override fun chooseUser(user: DataUi) {
                val bundle = bundleOf("userDetails" to user)
                findNavController().navigate(
                    R.id.action_mainFragment_to_userDetailsFragment,
                    bundle
                )
            }

            override fun dial(user: DataUi) {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:${user.phoneOperator}")
                ContextCompat.startActivity(requireContext(), intent, null)
            }
        })

        binding.rvFragmentMain.adapter = mAdapter

        vm.users.observe(viewLifecycleOwner) { listDataUi ->
            mList = listDataUi
            mAdapter.submitList(listDataUi)
        }

        vm.error.observe(viewLifecycleOwner) { errorType ->
            when (errorType.ordinal) {
                0 -> view.snackLong(R.string.no_connection_exception_message)
                1 -> view.snackLong(R.string.database_exception_message)
                2 -> view.snackLong(R.string.http_exception_message)
                3 -> view.snackLong(R.string.user_not_registered_exception_message)
                4 -> view.snackLong(R.string.database_exception_message)
                5 -> view.snackLong(R.string.generic_exception_message)
            }
        }

        searchService.mListFilteredLiveData.observe(viewLifecycleOwner) {
            mAdapter.submitList(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_main_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val menuItem = menu.findItem(R.id.fragmentMain_search)
        mSearchView = menuItem.actionView as SearchView
        search(mSearchView)
        super.onPrepareOptionsMenu(menu)
    }

    private fun search(searchView: SearchView) {
        searchView.setOnQueryTextListener(SearchViewListener { newText ->
            searchService.result(mList, newText)
        })
    }

    interface PermissionHandle {
        fun check()
    }
}