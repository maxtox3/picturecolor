package com.picturecolor.client.domain.repository

interface LoginRepository : Repository {

  suspend fun auth(firstName: String, secondName: String)
  suspend fun tryToAuth()
}