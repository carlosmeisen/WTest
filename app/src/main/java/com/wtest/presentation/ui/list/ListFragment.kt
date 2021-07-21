package com.wtest.presentation.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.wtest.R
import com.wtest.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {

    private lateinit var binding: FragmentListBinding
    private val viewModel: ListViewModel by viewModels()
    //lazy instance of the custom adapter
    private val listAdapter: PostalCodeListAdapter by lazy { PostalCodeListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //bind the view
        binding = FragmentListBinding.bind(view)

        binding.apply {

            //setting up recyclerview
            listRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireActivity())
                //setting the recycler view adapter as the custom adapter
                adapter = listAdapter
            }

            //observing the view model postal codes. Whenever the data gets updated the adapter will add
            //this data to the recycler view
            viewModel.postalCodes.observe(viewLifecycleOwner, {
                listAdapter.addHeaderAndSubmitList(it)
            })
        }
    }
}
