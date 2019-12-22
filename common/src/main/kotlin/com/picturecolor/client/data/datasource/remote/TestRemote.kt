package com.picturecolor.client.data.datasource.remote

import com.picturecolor.client.model.ImageModel
import com.picturecolor.client.model.TestModel

interface TestRemote {
  suspend fun getTest(token: String): TestModel
  suspend fun getTestImage(token: String, imageName: String, angle: Int): ImageModel
}