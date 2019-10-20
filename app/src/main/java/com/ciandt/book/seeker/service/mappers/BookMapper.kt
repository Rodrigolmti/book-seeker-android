package com.ciandt.book.seeker.service.mappers

import com.ciandt.book.seeker.domain.model.Book
import com.ciandt.book.seeker.service.model.BookResponse

fun BookResponse.mapBookResponseToBook(): Book = Book(
    name = this.trackName,
    authorName = this.artistName,
    description = this.description,
    coverImage = this.getBookImage()
)