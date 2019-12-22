package com.picturecolor.client.data.datasource.remote

interface LoginRemote {
  suspend fun auth(firstName: String, secondName: String): String
  suspend fun tryToAuth(): String
}