package com.ciandt.book.seeker.ui.search

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
import kotlinx.android.synthetic.main.search_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private val viewModel by viewModel<SearchViewModel>()
    private lateinit var historicAdapter: SearchHistoricAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.search_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        setupAdapter()
        addObservers()
        etQuery.onEditorAction = { query ->
            viewModel.updateQuery(query)
        }
        tvClear.setOnClickListener {
            handleRecentSectionVisibility(false)
            viewModel.clearHistoric()
        }
    }

    private fun addObservers() {
        lifecycle.addObserver(viewModel)
        viewModel.actionLiveData.observe(viewLifecycleOwner, Observer { singleEvent ->
            singleEvent.contentIfNotHandled?.let { action ->
                when (action) {
                    is SearchViewModelAction.QueryUpdated -> {
                        findNavController().navigate(R.id.action_searchFragment_to_searchResultFragment)
                    }
                    is SearchViewModelAction.HistoricList -> {
                        rvHistory.show()
                        progressBar.hide()
                        handleRecentSectionVisibility(action.list.isNotEmpty())
                        historicAdapter.updateHistoric(action.list)
                    }
                    is SearchViewModelAction.HistoricCleared -> {
                        historicAdapter.updateHistoric(emptyList())
                    }
                    is SearchViewModelAction.InvalidQuery -> {
                        etQuery.error = getString(R.string.search_fragment_invalid_query)
                    }
                    is SearchViewModelAction.QueryToSmall -> {
                        etQuery.error = getString(R.string.search_fragment_query_too_small)
                    }
                    is SearchViewModelAction.GetHistoricFail -> {
                        handleRecentSectionVisibility(false)
                    }
                }
            }
        })
    }

    private fun setupAdapter() {
        historicAdapter = SearchHistoricAdapter()
        rvHistory.layoutManager = LinearLayoutManager(context)
        rvHistory.adapter = historicAdapter
        historicAdapter.onClickListener = { query ->
            viewModel.updateQuery(query)
        }
    }

    private fun handleRecentSectionVisibility(visible: Boolean) {
        if (visible) {
            tvTrending.show()
            tvClear.show()
        } else {
            tvTrending.hide()
            tvClear.hide()
        }
    }
}