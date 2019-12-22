package com.picturecolor.client.domain.repository

import com.picturecolor.client.model.ImageModel
import com.picturecolor.client.model.TestModel

interface TestRepository : Repository {

  suspend fun getTest(): TestModel

  suspend fun getTestImage(imageName: String, angle: Int): ImageModel
}