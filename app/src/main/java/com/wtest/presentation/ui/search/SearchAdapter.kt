package com.wtest.presentation.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wtest.databinding.ItemPostalCodeBinding
import com.wtest.domain.model.PostalCode

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private var postalCodeData = emptyList<PostalCode>()

    class SearchViewHolder(val binding: ItemPostalCodeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            ItemPostalCodeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    //Binds the data from PostalCode object into the RecyclerView
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val completePostalCode =
            "${postalCodeData[position].postalCodeNum}-${postalCodeData[position].postalCodeExt}, ${postalCodeData[position].postalDesignation}"

        holder.binding.postalCodeItem.text = completePostalCode
    }

    //amount of data to be displayed in the view
    override fun getItemCount(): Int {
        return postalCodeData.size
    }

    //sets adapter data and notify the view that the data has been changed so it can be re-created
    fun setData(newData: List<PostalCode>) {
        postalCodeData = newData
        notifyDataSetChanged()
    }
}