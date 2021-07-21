package com.wtest.presentation.ui.list

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wtest.databinding.ItemPostalCodeBinding
import com.wtest.databinding.ListHeaderBinding
import com.wtest.domain.model.PostalCode
import kotlinx.coroutines.*
import kotlinx.coroutines.CoroutineScope
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

private const val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class PostalCodeListAdapter() :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(PostalCodeDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    //transforms PostalCode object into DataItem object
    fun addHeaderAndSubmitList(list: List<PostalCode>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map { DataItem.PostalCodeItem(it) }
            }
            withContext(Dispatchers.Main) {
                //submit the list
                submitList(items)
            }
        }
    }

    //binds the data into the respective view
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ListViewHolder -> {
                val postalCodeItem = getItem(position) as DataItem.PostalCodeItem
                holder.bind(postalCodeItem.postalCode)
            }
            is ImageViewHolder -> {
                holder.bind()
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> ImageViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ListViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.PostalCodeItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    //Header view holder. Download a random image through HttpURLConnection in the IO thread
    class ImageViewHolder(private val binding: ListHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val url =
                        URL("https://cafeinacodificada.com.br/wp-content/uploads/2019/02/kotlin.jpg")
                    val connection: HttpURLConnection = url
                        .openConnection() as HttpURLConnection
                    connection.doInput = true
                    connection.connect()
                    val input = connection.inputStream
                    val bitmapImage = BitmapFactory.decodeStream(input)
                    setImageToView(bitmapImage)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        //Set the downloaded image in the view in the Main Thread, cause view updates can only be done in this thread
        private fun setImageToView(bitmapImage: Bitmap) {
            CoroutineScope(Dispatchers.Main).launch {
                binding.listHeader.setImageBitmap(bitmapImage)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ImageViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListHeaderBinding.inflate(layoutInflater, parent, false)
                return ImageViewHolder(binding)
            }
        }
    }

    //Items view holder.
    class ListViewHolder private constructor(val binding: ItemPostalCodeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PostalCode) {
            binding.postalCodeItem.text = item.id.toString()
        }

        companion object {
            fun from(parent: ViewGroup): ListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemPostalCodeBinding.inflate(layoutInflater, parent, false)
                return ListViewHolder(binding)
            }
        }
    }

}

class PostalCodeDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

sealed class DataItem {
    data class PostalCodeItem(val postalCode: PostalCode) : DataItem() {
        override val id = postalCode.id
    }

    object Header : DataItem() {
        override val id = Long.MIN_VALUE
    }

    abstract val id: Long
}