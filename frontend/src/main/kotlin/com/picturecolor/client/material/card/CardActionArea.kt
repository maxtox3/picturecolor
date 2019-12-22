package com.picturecolor.client.material.card

import com.picturecolor.client.material.button.MButtonBaseProps
import com.picturecolor.client.material.createStyled
import com.picturecolor.client.material.setStyledPropsAndRunHandler
import org.w3c.dom.events.Event
import react.RBuilder
import react.RComponent
import react.RState
import styled.StyledHandler


@JsModule("@material-ui/core/CardActionArea")
private external val cardActionAreaModule: dynamic

@Suppress("UnsafeCastFromDynamic")
private val cardActionAreaComponent: RComponent<MButtonBaseProps, RState> = cardActionAreaModule.default

fun RBuilder.mCardActionArea(
        onClick: ((Event) -> Unit)? = null,
        disabled: Boolean = false,

        className: String? = null,
        handler: StyledHandler<MButtonBaseProps>? = null)= createStyled(cardActionAreaComponent) {
    attrs.disabled = disabled
    onClick?.let { attrs.onClick = onClick }

    setStyledPropsAndRunHandler(className, handler)
}
