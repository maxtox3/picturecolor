package com.picturecolor.client.presentation.di

import com.picturecolor.client.data.JsLocalDataSource
import com.picturecolor.client.data.datasource.remote.LoginRemote
import com.picturecolor.client.data.datasource.remote.LoginRemoteDataSource
import com.picturecolor.client.data.datasource.remote.ResearchRemoteDataSource
import com.picturecolor.client.data.datasource.remote.TestRemote
import com.picturecolor.client.domain.repository.LoginRepository
import com.picturecolor.client.domain.repository.LoginRepositoryImpl
import com.picturecolor.client.domain.repository.ResearchRepositoryImpl
import com.picturecolor.client.domain.repository.TestRepository
import com.picturecolor.client.newmvi.login.binder.LoginBinder
import com.picturecolor.client.newmvi.login.store.LoginProcessor
import com.picturecolor.client.newmvi.login.store.LoginProcessorImpl
import com.picturecolor.client.newmvi.login.store.LoginStore
import com.picturecolor.client.newmvi.login.store.LoginStoreImpl
import com.picturecolor.client.newmvi.researchlist.binder.TestBinder
import com.picturecolor.client.newmvi.researchlist.store.*

val local = JsLocalDataSource()

val remoteLoginDataSource: LoginRemote = LoginRemoteDataSource()
val loginRepository: LoginRepository = LoginRepositoryImpl(
  local = local,
  remote = remoteLoginDataSource
)

val remoteResearchDataSource: TestRemote = ResearchRemoteDataSource()
val testRepository: TestRepository = ResearchRepositoryImpl(
  local = local,
  remote = remoteResearchDataSource
)

val loginProcessor: LoginProcessor = LoginProcessorImpl(loginRepository)
val loginStore: LoginStore = LoginStoreImpl(loginProcessor)
val loginBinder = LoginBinder(loginStore)
fun injectLogin(): LoginBinder = loginBinder

val testLoader: TestLoader = TestLoaderImpl(testRepository)
val imageLoader: ImageLoader = ImageLoaderImpl(testRepository)
val researchListStore: TestStore = TestStoreImpl(testLoader, imageLoader)
val researchListBinder = TestBinder(researchListStore)
fun injectResearchList(): TestBinder = researchListBinder

//val researchLoader: ResearchDataLoader = ResearchDataLoaderImpl(testRepository)
//val marksLoader: ResearchMarksLoader = ResearchMarksLoaderImpl(testRepository)
//val researchStore: ResearchStore = ResearchStoreImpl(
//  researchLoader,
//  marksLoader
//)
//
//fun injectNewResearch(): ResearchBinder = ResearchBinder(
//  researchStore
//)