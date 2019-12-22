package com.picturecolor.client.newmvi.login.binder

import com.picturecolor.client.newmvi.login.store.LoginStore
import com.picturecolor.client.newmvi.login.view.LoginView

internal object LoginStateToViewModelMapper {

  operator fun invoke(state: LoginStore.State): LoginView.LoginViewModel =
    LoginView.LoginViewModel(
      isLoading = state.isLoading,
      error = state.error,
      authorized = state.authorized
    )
}