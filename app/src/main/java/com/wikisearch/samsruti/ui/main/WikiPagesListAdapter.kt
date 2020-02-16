package com.wikisearch.samsruti.ui.main


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import com.wikisearch.samsruti.databinding.ListItemPageBinding
import com.wikisearch.samsruti.model.Page

class WikiPagesListAdapter (private val clickListener: CallBackClickListener):
    PagedListAdapter<Page, WikiPagesListAdapter.WikiPagesViewHolder>(DiffCallback) {

    class WikiPagesViewHolder(private var dataBinding: ListItemPageBinding)
        : RecyclerView.ViewHolder(dataBinding.root) {

        fun bind(currentPage: Page){
            dataBinding.page = currentPage
            dataBinding.executePendingBindings()
        }

    }

    companion object DiffCallback: DiffUtil.ItemCallback<Page>(){
        override fun areItemsTheSame(oldItem: Page, newItem: Page): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Page, newItem: Page): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WikiPagesViewHolder {
        return WikiPagesViewHolder(ListItemPageBinding
            .inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: WikiPagesViewHolder, position: Int) {
        val currentPage = getItem(position)
        currentPage?.let {
            holder.itemView.setOnClickListener {
                clickListener.onClick(currentPage)
//            Toast.makeText(holder.itemView.context,currentPage.toString(),Toast.LENGTH_LONG).show()
            }
            holder.bind(currentPage)
        }


    }

    class CallBackClickListener(val clickListener: (networkNews: Page) -> Unit) {
        fun onClick(networkNews: Page) = clickListener(networkNews)
    }



}