package com.wikisearch.samsruti.model

import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.wikisearch.samsruti.db.WikiBigIntTypeConverter
import com.wikisearch.samsruti.db.WikiTypeConverter
import kotlinx.android.parcel.Parcelize
import java.math.BigInteger

@Entity(tableName = "page")
@TypeConverters(WikiTypeConverter::class, WikiBigIntTypeConverter::class)
data class Page(

    @PrimaryKey
    @field:SerializedName("pageid")
    val pageId: BigInteger,


//    @field:SerializedName("index")
//    val index: Int,
//
//    @field:SerializedName("ns")
//    val ns: Int,

    @Embedded
    @field:SerializedName("terms")
    val terms: Terms?,

    @Embedded
    @field:SerializedName("thumbnail")
    val thumbnail: Thumbnail?,

    @field:SerializedName("title")
    val title: String
)

@Parcelize
data class Terms(
    val description: List<String> = emptyList()
) : Parcelable

@Parcelize
data class Thumbnail(
    val height: Int,
    val source: String,
    val width: Int
) : Parcelable

