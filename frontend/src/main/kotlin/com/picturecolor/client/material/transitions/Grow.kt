package com.picturecolor.client.material.transitions

import com.picturecolor.client.material.createStyled
import com.picturecolor.client.material.setStyledPropsAndRunHandler
import react.RBuilder
import react.RComponent
import react.RState
import styled.StyledHandler


@JsModule("@material-ui/core/Grow")
private external val growModule: dynamic

@Suppress("UnsafeCastFromDynamic")
private val growComponent: RComponent<MGrowProps, RState> = growModule.default

external interface MGrowProps : MTransitionProps
var MGrowProps.timeout by TransitionDurationWithAutoDelegate()

fun RBuilder.mGrow(
        show: Boolean = false,
        timeout: TransitionDurationWithAuto? = null,

        addAsChild: Boolean = true,
        className: String? = null,
        handler: StyledHandler<MGrowProps>? = null) = createStyled(growComponent, addAsChild) {
    attrs.show = show
    timeout?.let { attrs.timeout = it }

    setStyledPropsAndRunHandler(className, handler)
}

