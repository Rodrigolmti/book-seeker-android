package com.ciandt.book.seeker.ui.reading_now

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ciandt.book.seeker.R
import org.koin.android.viewmodel.ext.android.viewModel

class ReadingNowFragment : Fragment() {

    private val viewModel by viewModel<ReadingNowViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.reading_now_fragment, container, false)
}