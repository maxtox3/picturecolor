package com.picturecolor.backend

import com.picturecolor.backend.model.User
import com.picturecolor.client.constants.AreaType
import com.picturecolor.client.model.SelectedArea
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toUser(): User = User(
  id = this[UserVos.id],
  name = this[UserVos.name],
  secondName = this[UserVos.secondName]
)

fun ResultRow.toMark(): SelectedArea = SelectedArea(
  x = this[MarkVos.x],
  y = this[MarkVos.y],
  z = this[MarkVos.z],
  type = when (this[MarkVos.type]) {
    0 -> AreaType.SOLID_NODULE
    1 -> AreaType.PART_SOLID_NODULE
    2 -> AreaType.PURE_SUBSOLID_NODULE
    else -> AreaType.NO_TYPE_NODULE
  },
  radius = this[MarkVos.radius],
  size = this[MarkVos.size],
  id = this[MarkVos.id]
)