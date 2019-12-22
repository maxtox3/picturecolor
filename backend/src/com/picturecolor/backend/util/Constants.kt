package com.picturecolor.backend.util

import com.picturecolor.backend.model.User
import io.ktor.application.ApplicationCall
import io.ktor.auth.authentication

const val AUTHENTICATION = "Authentication"
const val ID_FIELD = "id"
const val NAME_FIELD = "name"
const val PASSWORD_FIELD = "password"
const val SEEN_FIELD = "seen"
const val DONE_FIELD = "done"
const val USER_ID_FIELD = "user_id"
const val RESEARCH_ID_FIELD = "research_id"

const val USER_TABLE = "user"
const val RESEARCH_TABLE = "research"
const val MARKS_TABLE = "marks"


val data_store_path = "c:\\dicom"

val ApplicationCall.user get() = authentication.principal<User>()!!