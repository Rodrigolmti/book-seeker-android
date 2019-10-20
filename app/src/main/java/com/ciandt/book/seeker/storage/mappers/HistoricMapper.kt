package com.ciandt.book.seeker.storage.mappers

import com.ciandt.book.seeker.domain.model.Historic
import com.ciandt.book.seeker.storage.model.HistoricStorage

fun HistoricStorage.mapHistoricStorageToHistoric() = Historic(
    query = this.query
)