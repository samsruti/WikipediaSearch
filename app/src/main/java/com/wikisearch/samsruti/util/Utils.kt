package com.wikisearch.samsruti.util

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.wikisearch.samsruti.api.PageDomainModel
import com.wikisearch.samsruti.model.Page

//Just an enchancement feature for proper utilization of entities
fun Page.asDomainModel(): PageDomainModel {
    return PageDomainModel(
        pageId = pageId,
        terms = terms,
        thumbnail = thumbnail,
        title = title
    )
}

fun PageDomainModel.asDatabaseModel(): Page {
    return Page(
        pageId = pageId,
        terms = terms,
        thumbnail = thumbnail,
        title = title
    )
}

fun String.generateFullThumbnailSource(): String{
    return substring(0,indexOf("thumb")) + substring(indexOf("thumb") + "thumb".length + 1,lastIndexOf("/"))
}

fun Activity.hideKeyBoard(){
    val view = this.currentFocus
    view?.apply {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}