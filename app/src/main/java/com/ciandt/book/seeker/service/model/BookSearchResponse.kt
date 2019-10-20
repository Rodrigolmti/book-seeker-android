package com.ciandt.book.seeker.service.model

import com.google.gson.annotations.SerializedName

data class BookSearchResponse(
    @SerializedName("resultCount") val resultCount: Int,
    @SerializedName("results") val results: List<BookResponse>
)

data class BookResponse(
    @SerializedName("artistName") val artistName: String,
    @SerializedName("trackName") val trackName: String,
    @SerializedName("artworkUrl30") val artworkUrl30: String? = null,
    @SerializedName("artworkUrl60") val artworkUrl60: String? = null,
    @SerializedName("artworkUrl100") val artworkUrl100: String? = null,
    @SerializedName("description") val description: String
) {
    fun getBookImage(): String {
        if (artworkUrl100 != null) return artworkUrl100
        if (artworkUrl60 != null) return artworkUrl60
        if (artworkUrl30 != null) return artworkUrl30
        return ""
    }
}

