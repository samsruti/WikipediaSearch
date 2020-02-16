package com.wikisearch.samsruti.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.wikisearch.samsruti.databinding.WikiDetailsFragmentBinding

class WikiDetailsFragment : Fragment() {



    private lateinit var viewModel: WikiDetailsViewModel
    private val pageDetailsArgs: WikiDetailsFragmentArgs by navArgs()

    private lateinit var dataBinding: WikiDetailsFragmentBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataBinding = WikiDetailsFragmentBinding.inflate(inflater,container,false)
        dataBinding.pageDetails = pageDetailsArgs.page
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WikiDetailsViewModel::class.java)
    }

}
