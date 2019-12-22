package com.picturecolor.client.data

import com.picturecolor.client.constants.TOKEN
import com.picturecolor.client.data.datasource.local.LocalDataSource
import org.w3c.dom.set
import kotlin.browser.localStorage


class JsLocalDataSource : LocalDataSource {

  override fun saveToken(token: String) {
    localStorage[TOKEN] = token
  }

  override fun getToken(): String = localStorage.getItem(TOKEN) ?: ""

}