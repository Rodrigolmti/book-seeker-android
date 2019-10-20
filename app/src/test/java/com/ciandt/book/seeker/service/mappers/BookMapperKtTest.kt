package com.ciandt.book.seeker.service.mappers

import com.ciandt.book.seeker.TestDataFactory
import com.ciandt.book.seeker.service.model.BookResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import kotlin.test.assertEquals

internal class BookMapperKtTest {

    @Test
    fun `call map book response should return book model`() {
        val bookResponse = TestDataFactory.makeBookResponse()

        val result = bookResponse.mapBookResponseToBook()

        assertEquals(bookResponse.artistName, result.authorName)
        assertEquals(bookResponse.trackName, result.name)
        assertEquals(bookResponse.description, result.description)
    }
}