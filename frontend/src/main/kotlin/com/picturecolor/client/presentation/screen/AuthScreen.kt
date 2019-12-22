package com.picturecolor.client.presentation.screen

import com.badoo.reaktive.subject.publish.PublishSubject
import com.badoo.reaktive.subject.publish.publishSubject
import com.picturecolor.client.material.*
import com.picturecolor.client.material.button.*
import com.picturecolor.client.material.form.MFormControlMargin
import com.picturecolor.client.material.form.MFormControlVariant
import com.picturecolor.client.newmvi.login.view.LoginView
import com.picturecolor.client.presentation.di.injectLogin
import kotlinx.css.*
import kotlinx.html.js.onSubmitFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import styled.*

class AuthScreen(props: AuthProps) :
  RComponent<AuthProps, AuthState>(props), LoginView {

  private val binder = injectLogin()
  override val events: PublishSubject<LoginView.Event> = publishSubject()

  override fun show(model: LoginView.LoginViewModel) {
    if (model.authorized) {
      props.authorized()
    } else {
      setState {
        loading = model.isLoading
        error = model.error
      }
    }
  }

  override fun componentDidMount() {
    binder.attachView(this@AuthScreen)
    binder.onStart()
    dispatch(LoginView.Event.Init)
  }

  override fun RBuilder.render() {
    themeContext.Consumer { theme ->
      mContainer {
        attrs {
          component = "main"
          maxWidth = "xs"
        }
        mCssBaseline()
        styledDiv {
          css {
            marginTop = 9.spacingUnits
            display = Display.flex
            flexDirection = FlexDirection.column
            alignItems = Align.center
          }
          mAvatar {
            css {
              margin(1.spacingUnits)
              backgroundColor = Color(theme.palette.secondary.main)
            }
            mIcon("lock_open")
          }
          mTypography {
            attrs {
              component = "h1"
              variant = MTypographyVariant.h5
            }
            +"Давайте знакомиться"
          }
          styledForm {
            attrs.onSubmitFunction = {
              //todo()
            }
            css {
              width = 100.pct
              marginTop = 1.spacingUnits
            }
            attrs {
              novalidate = true
            }
            mTextField(
              variant = MFormControlVariant.outlined,
              margin = MFormControlMargin.normal,
              id = "firstName",
              label = "Ваше имя",
              name = "firstName",
              autoComplete = "firstName",
              autoFocus = true,
              fullWidth = true
            ) {
              attrs {
                required = true
                onChange = ::onFirstNameChanged
                inputLabelProps = object : RProps {
                  val shrink = true
                }
              }
            }
            mTextField(
              variant = MFormControlVariant.outlined,
              margin = MFormControlMargin.normal,
              id = "secondName",
              label = "Ваша фамилия",
              name = "secondName",
              autoComplete = "secondName",
              fullWidth = true
            ) {
              attrs {
                required = true
                onChange = ::onSecondNameChanged
                inputLabelProps = object : RProps {
                  val shrink = true
                }
              }
            }
            styledDiv {
              css {
                margin(3.spacingUnits, 0.spacingUnits, 2.spacingUnits)
                position = Position.relative
              }
              mButton(
                "Начнём!",
                size = MButtonSize.large,
                disabled = state.loading,
                variant = MButtonVariant.contained,
                color = MColor.primary
              ) {
                attrs {
                  fullWidth = true
                  onClick = {
                    dispatch(LoginView.Event.Auth(state.firstName, state.secondName))
                  }
                }
              }
              if (state.loading) {
                mCircularProgress(size = 24.px) {
                  css {
                    color = Colors.Blue.shade500
                    position = Position.absolute
                    top = 50.pct
                    left = 50.pct
                    marginTop = -12.px
                    marginLeft = -12.px
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  private fun onSecondNameChanged(event: Event) {
    val target = event.target as HTMLInputElement
    val searchTerm = target.value
    setState {
      secondName = searchTerm
    }
  }

  private fun onFirstNameChanged(event: Event) {
    val target = event.target as HTMLInputElement
    val searchTerm = target.value
    setState {
      firstName = searchTerm
    }
  }

  override fun componentWillUnmount() {
    binder.detachView()
    binder.onStop()
  }

  override fun dispatch(event: LoginView.Event) {
    events.onNext(event)
  }


}

class AuthState : RState {
  var loading: Boolean = true
  var error: String = ""
  var firstName: String = ""
  var secondName: String = ""
}


interface AuthProps : RProps {
  var authorized: () -> Unit
}

fun RBuilder.auth(authListener: () -> Unit) = child(AuthScreen::class) {
  attrs.authorized = authListener
}