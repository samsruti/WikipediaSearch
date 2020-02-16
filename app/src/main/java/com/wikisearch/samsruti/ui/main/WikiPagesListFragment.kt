package com.wikisearch.samsruti.ui.main

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import com.wikisearch.samsruti.DependencyInjection
import com.wikisearch.samsruti.MainActivity
import com.wikisearch.samsruti.R
import com.wikisearch.samsruti.databinding.MainFragmentBinding
import com.wikisearch.samsruti.model.Page
import com.wikisearch.samsruti.util.asDomainModel
import com.wikisearch.samsruti.util.hideKeyBoard

class WikiPagesListFragment : Fragment() {


    private lateinit var viewModel: WikiPagesViewModel

    private var wikiPagesListAdapter: WikiPagesListAdapter? = null

    private lateinit var dataBinding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = MainFragmentBinding.inflate(inflater,container,false)

        dataBinding.lifecycleOwner = this

        setHasOptionsMenu(true)


        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
            this, DependencyInjection.provideViewModelFactory(dataBinding.root.context)
                    )
                    .get(WikiPagesViewModel::class.java)

        dataBinding.viewModel = viewModel

        val decoration = DividerItemDecoration(dataBinding.root.context, DividerItemDecoration.VERTICAL)
        dataBinding.pagesRecyclerView.addItemDecoration(decoration)

        initAdapter()

        viewModel.navigateToSelectedPage.observe(this, Observer {
            if (it!=null){
                activity?.hideKeyBoard()
                this.findNavController().navigate(WikiPagesListFragmentDirections
                    .actionMainFragmentToWikiDetails(it.asDomainModel()))

                viewModel.displayPageCompleted()
            }
        })

        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY

    }

    private fun initAdapter() {


        wikiPagesListAdapter = WikiPagesListAdapter(WikiPagesListAdapter.CallBackClickListener{
            Toast.makeText(dataBinding.root.context,"$it",Toast.LENGTH_LONG).show()
            viewModel.displayPageDetails(it)
        })


        dataBinding.pagesRecyclerView.adapter = wikiPagesListAdapter


        viewModel.pages.observe(viewLifecycleOwner, Observer<PagedList<Page>> { pages ->
            Log.d("Activity", "list: ${pages?.size}")
            wikiPagesListAdapter?.submitList(pages)

        })

        viewModel.networkErrors.observe(this, Observer<String> {
            Toast.makeText(dataBinding.root.context,"Error! $it",Toast.LENGTH_SHORT).show()
        })
    }


    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = "Wikipedia"
    }




    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_top_menu, menu)

        val searchItem = menu.findItem(R.id.menu_search)
        val searchView = searchItem.actionView as SearchView

        searchView.queryHint = "Find in Wiki.."

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
               resetAdapter()
                return true
            }

        })


        val searchManager = dataBinding.root.context.getSystemService(Context.SEARCH_SERVICE) as SearchManager

            if(searchItem != null) {

                    searchView.setSearchableInfo(searchManager.getSearchableInfo((activity as MainActivity).componentName))

                    searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                        override fun onQueryTextSubmit(query: String): Boolean {

                            SearchRecentSuggestions(
                                dataBinding.root.context, MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE
                            ).saveRecentQuery(query, null)

                            viewModel.searchRepo(query)
                            return true
                        }

                        override fun onQueryTextChange(newText: String): Boolean {

                            if(newText.isEmpty()) {
                                resetAdapter()
                            } else {
                                Toast.makeText(dataBinding.root.context, newText, Toast.LENGTH_SHORT).show()
                                Log.i("New Text", newText)
                                viewModel.searchRepo(newText)
                            }
                            return true
                        }

                    })

                    searchView.setOnCloseListener {
                        resetAdapter()
                        false
                    }

            }

    }

    private fun resetAdapter() {
        wikiPagesListAdapter?.submitList(null)
        wikiPagesListAdapter?.notifyDataSetChanged()
    }

}
