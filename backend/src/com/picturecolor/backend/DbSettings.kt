package com.picturecolor.backend

import com.picturecolor.backend.util.*
import com.picturecolor.client.constants.AreaType
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object UserVos : Table(name = USER_TABLE) {
  val id: Column<Int> = integer(name = ID_FIELD).autoIncrement().primaryKey()
  val name: Column<String> = varchar(name = NAME_FIELD, length = 100).uniqueIndex()
  val secondName: Column<String> = varchar(name = PASSWORD_FIELD, length = 200)
}

object ResearchVos : Table(name = RESEARCH_TABLE) {
  val id: Column<Int> = integer(name = ID_FIELD).autoIncrement().primaryKey()
  val name: Column<String> = varchar(name = NAME_FIELD, length = 200).uniqueIndex()
}

object UserResearchVos : Table("user_research") {
  val userId: Column<Int> = integer("user_$ID_FIELD").primaryKey()
  val researchId: Column<Int> = integer("research_$ID_FIELD").primaryKey()
  val seen: Column<Int> = integer(name = SEEN_FIELD).default(0)
  val done: Column<Int> = integer(name = DONE_FIELD).default(0)
}

object MarkVos : Table(name = MARKS_TABLE) {
  val id: Column<Int> = integer(name = ID_FIELD).autoIncrement().primaryKey()
  val userId: Column<Int> = integer(name = USER_ID_FIELD)
  val researchId: Column<Int> = integer(name = RESEARCH_ID_FIELD)
  val x: Column<Double> = double(name = "x")
  val y: Column<Double> = double(name = "y")
  val z: Column<Double> = double(name = "z")
  val type: Column<Int> = integer(name = "type").default(AreaType.NO_TYPE_NODULE.value)
  val radius: Column<Double> = double(name = "radius")
  val size: Column<Double> = double(name = "size")
}