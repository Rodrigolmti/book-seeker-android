package com.ciandt.book.seeker.domain.use_case

import com.ciandt.book.seeker.domain.Failure
import com.ciandt.book.seeker.domain.Result
import com.ciandt.book.seeker.domain.repository.Repository

class UpdateQueryUseCase(
    private val repository: Repository
) {

    class UpdateQueryFailure(val errors: List<ErrorType>) : Failure.FeatureFailure() {
        enum class ErrorType {
            INVALID_QUERY, INVALID_QUERY_LENGTH
        }
    }

    suspend operator fun invoke(query: String): Result<Unit> {
        val errors = arrayListOf<UpdateQueryFailure.ErrorType>()

        takeIf {
            query.isEmpty()
        }?.let { errors.add(UpdateQueryFailure.ErrorType.INVALID_QUERY) }
        takeIf {
            query.length < 3
        }?.let { errors.add(UpdateQueryFailure.ErrorType.INVALID_QUERY_LENGTH) }

        return if (errors.isEmpty()) {
            Result.Success(repository.updateQuery(query))
        } else {
            Result.Error(UpdateQueryFailure(errors))
        }
    }
}