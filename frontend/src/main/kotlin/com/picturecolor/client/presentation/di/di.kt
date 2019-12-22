package com.picturecolor.client.presentation.di

import com.picturecolor.client.data.JsLocalDataSource
import com.picturecolor.client.data.datasource.remote.*
import com.picturecolor.client.domain.repository.*
import com.picturecolor.client.newmvi.ResearchContainer
import com.picturecolor.client.newmvi.login.binder.LoginBinder
import com.picturecolor.client.newmvi.login.store.*
import com.picturecolor.client.newmvi.researchlist.binder.ResearchListBinder
import com.picturecolor.client.newmvi.researchlist.store.*
import com.picturecolor.client.newmvi.researchmvi.binder.ResearchBinder
import com.picturecolor.client.newmvi.researchmvi.store.*

val local = JsLocalDataSource()

val remoteLoginDataSource: LoginRemote = LoginRemoteDataSource()
val loginRepository: LoginRepository = LoginRepositoryImpl(
  local = local,
  remote = remoteLoginDataSource
)

val remoteResearchDataSource: ResearchRemote = ResearchRemoteDataSource()
val researchRepository: ResearchRepository = ResearchRepositoryImpl(
  local = local,
  remote = remoteResearchDataSource
)

val loginProcessor: LoginProcessor = LoginProcessorImpl(loginRepository)
val loginStore: LoginStore = LoginStoreImpl(loginProcessor)
val loginBinder = LoginBinder(loginStore)
fun injectLogin(): LoginBinder = loginBinder

val researchListLoader: ResearchListLoader = ResearchListLoaderImpl(researchRepository)
val researchListStore: ResearchListStore = ResearchListStoreImpl(researchListLoader)
val researchListBinder = ResearchListBinder(researchListStore)
fun injectResearchList(): ResearchListBinder = researchListBinder

val researchLoader: ResearchDataLoader = ResearchDataLoaderImpl(researchRepository)
val marksLoader: ResearchMarksLoader = ResearchMarksLoaderImpl(researchRepository)
val researchStore: ResearchStore = ResearchStoreImpl(
  researchLoader,
  marksLoader
)

fun injectNewResearch(): ResearchBinder = ResearchBinder(
  researchStore,
  ResearchContainer.deleteAreaObservable,
  ResearchContainer.newAreaToSaveObservable,
  ResearchContainer.areaToUpdateObservable
)