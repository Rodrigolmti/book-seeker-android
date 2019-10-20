package com.ciandt.book.seeker.ui.book_store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ciandt.book.seeker.R
import com.ciandt.book.seeker.ui.reading_now.ReadingNowViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class BookStoreFragment : Fragment() {

    private val viewModel by viewModel<BookStoreViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.library_fragment, container, false)
}