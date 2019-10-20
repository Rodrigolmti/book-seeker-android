package com.ciandt.book.seeker.domain.use_case

import com.ciandt.book.seeker.domain.repository.Repository

class ClearHistoricUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke() = repository.clearHistoric()
}