package com.wikisearch.samsruti.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass
import com.wikisearch.samsruti.model.Page
import com.wikisearch.samsruti.model.Terms
import com.wikisearch.samsruti.model.Thumbnail
import com.wikisearch.samsruti.util.generateFullThumbnailSource
import kotlinx.android.parcel.Parcelize
import java.math.BigInteger

@JsonClass(generateAdapter = true)
data class NetworkResponse(

    @SerializedName("batchcomplete")
    val batchComplete: Boolean,


    @SerializedName("query")
    val query: Query
)


data class Query(
    @SerializedName("pages")
    val pages: List<Page>,

    @SerializedName("redirects")
    val redirects: List<Redirect>
)

data class Redirect(
    val from: String,
    val index: Int,
    val to: String
)

@Parcelize
data class PageDomainModel(

    @SerializedName("pageid")
    val pageId: BigInteger,

    @SerializedName("terms")
    val terms: Terms?,

    @SerializedName("thumbnail")
    val thumbnail: Thumbnail?,

    @SerializedName("title")
    val title: String

) : Parcelable {
    val fullImageViewUrl: String
        get() = thumbnail?.source?.generateFullThumbnailSource() ?: ""
}



