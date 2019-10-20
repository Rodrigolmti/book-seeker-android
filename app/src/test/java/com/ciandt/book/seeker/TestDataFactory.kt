package com.ciandt.book.seeker

import com.ciandt.book.seeker.domain.model.Book
import com.ciandt.book.seeker.domain.model.Historic
import com.ciandt.book.seeker.service.model.BookResponse
import com.ciandt.book.seeker.storage.model.HistoricStorage

object TestDataFactory {

    fun makeBook() = Book(
        name = "A senha que não esra senha",
        authorName = "Alehandro farijo",
        description = "Desccubra o porque a senha era uma senha",
        coverImage = "senha.png"
    )

    fun makeBookResponse() = BookResponse(
        artistName = "Asimov",
        trackName = "Foundation",
        artworkUrl30 = "img.png",
        artworkUrl60 = "img.png",
        artworkUrl100 = "img.png",
        description = "Foundation book about robots"
    )

    fun makeHistoric() = Historic(query = "android")

    fun makeHistoricStorage() = HistoricStorage(1, "asimov")
}