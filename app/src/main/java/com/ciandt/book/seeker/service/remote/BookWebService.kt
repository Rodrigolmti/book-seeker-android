package com.ciandt.book.seeker.service.remote

import com.ciandt.book.seeker.service.model.BookSearchResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface BookWebService {

    @GET("search?&entity=ibook")
    fun searchBooksByName(
        @Query("term") query: String
    ): Deferred<BookSearchResponse>
}