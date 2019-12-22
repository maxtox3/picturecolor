package com.picturecolor.client.newmvi.login.view

import com.badoo.reaktive.subject.publish.PublishSubject
import com.picturecolor.client.newmvi.researchmvi.BaseEvent
import com.picturecolor.client.newmvi.researchmvi.BaseView

interface LoginView : BaseView<LoginView.Event> {

  val events: PublishSubject<Event>

  fun show(model: LoginViewModel)

  data class LoginViewModel(
    val isLoading: Boolean,
    val error: String,
    val authorized: Boolean
  )

  sealed class Event : BaseEvent {
    object Init : Event()
    object ErrorShown : Event()
    data class Auth(val firstName: String, val secondName: String) : Event()
  }
}