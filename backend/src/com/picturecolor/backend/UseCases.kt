package com.picturecolor.backend

import com.picturecolor.backend.model.User
import com.picturecolor.client.constants.AreaType
import com.picturecolor.client.model.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun init() {
  transaction {
    SchemaUtils.create(
      UserVos
    )
  }
}

fun createUser(firstName: String, secondName: String): User = transaction {
  val insert = UserVos.insert {
    it[name] = firstName
    it[this.secondName] = secondName
  } get UserVos.id
  return@transaction User(insert, firstName, secondName)
}

fun getUser(id: Int): User = transaction {
  UserVos.select {
    UserVos.id eq id
  }.first()
}.toUser()

fun getResearches(userId: Int): List<Research> {
  return transaction {
    UserResearchVos.select {
      UserResearchVos.userId eq userId
    }.toList()
  }.map {
    val id = it[UserResearchVos.researchId]
    val seen = it[UserResearchVos.seen]
    val done = it[UserResearchVos.done]

    Research(
      id = id,
      name = transaction {
        ResearchVos.select { ResearchVos.id eq id }.first()
      }.let { result -> result[ResearchVos.name] },
      seen = seen == 1,
      done = done == 1
    )
  }
}

fun getResearch(researchId: Int, userId: Int): Research {
  return transaction {
    ResearchVos.select {
      ResearchVos.id eq researchId
    }.first()
  }.let {
    val userResearchResultRow = transaction {
      UserResearchVos.select {
        (UserResearchVos.userId eq userId) and (UserResearchVos.researchId eq researchId)
      }.first()
    }
    Research(
      id = it[ResearchVos.id],
      name = it[ResearchVos.name],
      seen = userResearchResultRow[UserResearchVos.seen] == 1,
      done = userResearchResultRow[UserResearchVos.done] == 1
    )
  }
}

fun updateResearches(accessionNumbers: Array<String>) {
  transaction {
    accessionNumbers.forEach { accessionNumber ->
      ResearchVos.insertIgnore {
        it[name] = accessionNumber
      } //get ResearchVos.id
    }
    val users = UserVos.selectAll().map { row -> row.toUser() }
    val researches = ResearchVos.selectAll().map { resultRow -> resultRow[ResearchVos.id] }
    users.forEach { user ->
      researches.forEach { resId ->
        UserResearchVos.insertIgnore {
          it[userId] = user.id
          it[researchId] = resId
        }

      }
    }
  }
}

fun saveMark(userrId: Int, researchhId: Int, area: AreaToSave): SelectedArea = transaction {
  val insert = MarkVos.insert {
    it[userId] = userrId
    it[researchId] = researchhId
    it[x] = area.x
    it[y] = area.y
    it[z] = area.z
    it[type] = AreaType.NO_TYPE_NODULE.value
    it[radius] = area.radius
    it[size] = area.size
  } get MarkVos.id

  MarkVos.select {
    MarkVos.id eq insert
  }.first()

}.toMark()

fun getMarks(userrId: Int, researchhId: Int): List<SelectedArea> = transaction {
  MarkVos.select {
    (MarkVos.userId eq userrId) and (MarkVos.researchId eq researchhId)
  }.toList()
}.map { row ->
  row.toMark()
}

fun updateMark(id: Int, selectedArea: SelectedArea) = transaction {
  MarkVos.update({ MarkVos.id eq id }) {
    it[x] = selectedArea.x
    it[y] = selectedArea.y
    it[z] = selectedArea.z
    it[type] = selectedArea.type.value
    it[radius] = selectedArea.radius
    it[size] = selectedArea.size
  }
}

fun deleteMark(id: Int) = transaction {
  MarkVos.deleteWhere { MarkVos.id eq id }
}
