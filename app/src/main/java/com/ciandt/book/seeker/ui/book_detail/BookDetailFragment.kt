package com.ciandt.book.seeker.ui.book_detail

import android.os.Bundle
import android.text.Html
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ciandt.book.seeker.R
import com.ciandt.book.seeker.domain.model.Book
import kotlinx.android.synthetic.main.book_detail_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class BookDetailFragment : Fragment() {

    private val viewModel by viewModel<BookDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.book_detail_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        lifecycle.addObserver(viewModel)
        ivClose.setOnClickListener {
            findNavController().popBackStack()
        }
        addObservers()
    }

    private fun addObservers() {
        viewModel.actionLiveData.observe(viewLifecycleOwner, Observer { action ->
            when (action) {
                is BookDetailViewModelAction.BookRetrieved -> {
                    setupBookData(action.book)
                }
            }
        })
    }

    private fun setupBookData(book: Book) {
        context?.let { context ->
            Glide.with(context)
                .load(book.coverImage)
                .centerCrop()
                .into(ivCover)
        }
        tvTitle.text = book.name
        tvAuthor.text = book.authorName
        tvDescription.text = Html.fromHtml(book.description)
    }
}