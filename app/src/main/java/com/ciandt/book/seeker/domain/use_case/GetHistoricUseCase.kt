package com.ciandt.book.seeker.domain.use_case

import com.ciandt.book.seeker.domain.Result
import com.ciandt.book.seeker.domain.model.Historic
import com.ciandt.book.seeker.domain.repository.Repository

class GetHistoricUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(): Result<List<Historic>> = repository.getListOfHistoric()
}