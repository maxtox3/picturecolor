package com.picturecolor.backend

import com.picturecolor.backend.model.User
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toUser(): User = User(
  id = this[UserVos.id],
  name = this[UserVos.name],
  secondName = this[UserVos.secondName]
)
