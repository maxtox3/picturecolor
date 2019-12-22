package com.picturecolor.client.model

import kotlinx.serialization.Serializable

@Serializable
data class ImageResponse(
  val data: ImageModel
)

@Serializable
data class ImageModel(
  val src: String,
  val angle: Int,
  val name: String
)

@Serializable
data class TestResponse(
  val data: TestModel
)

@Serializable
data class TestModel(
  val availableImages: List<String>
)