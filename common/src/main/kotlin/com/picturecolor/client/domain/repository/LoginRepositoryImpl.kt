package com.picturecolor.client.domain.repository

import com.picturecolor.client.data.datasource.local.LocalDataSource
import com.picturecolor.client.data.datasource.remote.LoginRemote
import com.picturecolor.client.debugLog

class LoginRepositoryImpl(
  private val local: LocalDataSource,
  private val remote: LoginRemote
) : LoginRepository {

  override suspend fun auth(firstName: String, secondName: String) {
    debugLog("repository:    name: $firstName password:$secondName")
    val token = remote.auth(firstName, secondName)
    local.saveToken(token = token)
  }

  override suspend fun tryToAuth() {
    val token = local.getToken()
    if (token.isNotEmpty()) {
      remote.tryToAuth()
    } else {
      throw Exception()
    }
  }

}