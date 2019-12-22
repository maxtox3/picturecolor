package com.picturecolor.client.newmvi

import com.picturecolor.client.model.CircleShape
import com.picturecolor.client.model.SelectedArea
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

object AreaToSagittalCircleMapper {

  fun invoke(
    sliceNumber: Int,
    area: SelectedArea,
    coefficient: Double,
    selectedAreaId: Int,
    cutToScreenCoefficient: Double
  ): CircleShape? {
    return if ((sliceNumber < (area.x + area.radius)) && (sliceNumber > (area.x - area.radius))) {
      val x = area.y* cutToScreenCoefficient
      val y = area.z * coefficient* cutToScreenCoefficient
      val h = abs(sliceNumber - area.x)* cutToScreenCoefficient
      val newRadius = sqrt((area.radius * cutToScreenCoefficient).pow(2) - h.pow(2))
      CircleShape(
        x = x,
        y = y,
        radius = newRadius,
        areaId = area.id,
        highlight = selectedAreaId == area.id
      )
    } else {
      null
    }
  }
}