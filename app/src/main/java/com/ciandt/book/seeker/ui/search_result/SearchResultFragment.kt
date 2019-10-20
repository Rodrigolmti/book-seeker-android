package com.ciandt.book.seeker.ui.search_result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciandt.book.seeker.R
import com.ciandt.book.seeker.util.hide
import com.ciandt.book.seeker.util.show
import kotlinx.android.synthetic.main.partial_toolbar.*
import kotlinx.android.synthetic.main.search_result_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class SearchResultFragment : Fragment() {

    private val viewModel by viewModel<SearchResultViewModel>()
    private lateinit var booksAdapter: BooksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.search_result_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        setupAdapter()
        addObservers()
        ivBack.setOnClickListener {
            viewModel.clearQuery()
        }
    }

    private fun addObservers() {
        lifecycle.addObserver(viewModel)
        viewModel.actionLiveData.observe(viewLifecycleOwner, Observer { singleEvent ->
            singleEvent.contentIfNotHandled?.let { action ->
                when (action) {
                    is SearchResultViewModelAction.BookList -> {
                        progressBar.hide()
                        handleErrorVisibility(false)
                        booksAdapter.updateListOfBooks(action.list)
                    }
                    is SearchResultViewModelAction.BookUpdated -> {
                        findNavController()
                            .navigate(R.id.action_searchResultFragment_to_bookDetailFragment)
                    }
                    is SearchResultViewModelAction.GetBookListFail -> {
                        handleErrorVisibility(true)
                        progressBar.hide()
                    }
                    is SearchResultViewModelAction.QueryCleared -> {
                        findNavController().popBackStack()
                    }
                }
            }
        })
    }

    private fun setupAdapter() {
        booksAdapter = BooksAdapter()
        rvBooks.layoutManager = LinearLayoutManager(context)
        rvBooks.adapter = booksAdapter
        booksAdapter.onClickListener = { book ->
            viewModel.updateBook(book)
        }
    }

    private fun handleErrorVisibility(visible: Boolean) {
        if (visible) {
            tvError.show()
            rvBooks.hide()
        } else {
            tvError.hide()
            rvBooks.show()
        }
    }
}