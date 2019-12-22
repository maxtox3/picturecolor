package com.picturecolor.client.domain.repository

import com.picturecolor.client.data.datasource.local.LocalDataSource
import com.picturecolor.client.data.datasource.remote.TestRemote
import com.picturecolor.client.model.ImageModel
import com.picturecolor.client.model.TestModel

class ResearchRepositoryImpl(
  private val local: LocalDataSource,
  private val remote: TestRemote
) : TestRepository {

  override suspend fun getTest(): TestModel {
    return remote.getTest(local.getToken())
  }

  override suspend fun getTestImage(imageName: String, angle: Int): ImageModel {
    return remote.getTestImage(local.getToken(), imageName, angle)
  }
}