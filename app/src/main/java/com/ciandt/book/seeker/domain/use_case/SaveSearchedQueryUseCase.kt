package com.ciandt.book.seeker.domain.use_case

import com.ciandt.book.seeker.domain.repository.Repository

class SaveSearchedQueryUseCase(
    private val repository: Repository,
    private val getQueryUseCase: GetQueryUseCase
) {

    suspend operator fun invoke() {
        getQueryUseCase()?.let { query ->
            repository.saveSearchedQuery(query)
        }
    }
}