package com.wikisearch.samsruti.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.math.BigInteger

object WikiTypeConverter{

    @TypeConverter
    @JvmStatic
    fun listToJson(description: List<String>): String?{
        return Gson().toJson(description)
    }

    @TypeConverter
    @JvmStatic
    fun jsonToList(descriptionAsJson: String): List<String>?{
        return (Gson().fromJson(descriptionAsJson, Array<String>::class.java) as Array<String>).toList()
    }

}

object WikiBigIntTypeConverter{

    @TypeConverter
    @JvmStatic
    fun bigIntToString(bigInteger: BigInteger): String{
        return bigInteger.toString()
    }

    @TypeConverter
    @JvmStatic
    fun bigIntToString(bigIntegerString: String): BigInteger{
        return bigIntegerString.toBigIntegerOrNull() ?: BigInteger.ZERO
    }

}