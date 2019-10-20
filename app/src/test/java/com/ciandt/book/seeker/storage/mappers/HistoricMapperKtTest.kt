package com.ciandt.book.seeker.storage.mappers

import com.ciandt.book.seeker.TestDataFactory
import org.junit.Test
import kotlin.test.assertEquals

internal class HistoricMapperKtTest {

    @Test
    fun `call map historic storage should return historic model`() {
        val historicStorage = TestDataFactory.makeHistoricStorage()

        val result = historicStorage.mapHistoricStorageToHistoric()

        assertEquals(historicStorage.query, result.query)
    }
}