package com.wtest.presentation.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.wtest.R
import com.wtest.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var binding: FragmentSearchBinding
    private val searchAdapter: SearchAdapter by lazy { SearchAdapter() }
    private val viewModel: SearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //binds the view
        binding = FragmentSearchBinding.bind(view)

        binding.apply {

            //added a clicked listener on the button to execute the search.
            //For performance reasons, since the database have over 350k+ items,
            //I chose this approach over an onTextChangeListener in the EditText
            searchButton.setOnClickListener {
                searchPostalCode(searchEditText.text.toString())
            }

            searchRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireActivity())
                //setting the recycler view adapter as the custom adapter
                adapter = searchAdapter

            }

            viewModel.apply {
                //observing the view model. Whenever the data gets updated the adapter will add
                //this data to the recycler view
                postalCodes.observe(viewLifecycleOwner, {
                    searchAdapter.setData(it)
                })
            }
        }
    }

    private fun searchPostalCode(query: String) {
        //receive the text parameter to be searched in the data base and ask the viewModel to retrieve the data.
        //observe the result and set it to the adapter to update the view
        val searchQuery = "%$query%"

        viewModel.searchPostalCode(searchQuery).observe(viewLifecycleOwner, { list ->
            list.let {
                searchAdapter.setData(list)
            }
        })
    }
}