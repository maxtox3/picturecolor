package com.picturecolor.client.material.card

import com.picturecolor.client.material.createStyled
import com.picturecolor.client.material.setStyledPropsAndRunHandler
import react.RBuilder
import react.RComponent
import react.RState
import styled.StyledHandler
import styled.StyledProps


@JsModule("@material-ui/core/CardActions")
private external val cardActionsModule: dynamic

@Suppress("UnsafeCastFromDynamic")
private val cardActionsComponent: RComponent<MCardActionsProps, RState> = cardActionsModule.default

interface MCardActionsProps : StyledProps {
    var disableSpacing: Boolean
}

fun RBuilder.mCardActions(disableSpacing: Boolean = false,
                          handler: StyledHandler<MCardActionsProps>?) = createStyled(cardActionsComponent) {
    attrs.disableSpacing = disableSpacing
    setStyledPropsAndRunHandler(null, handler)
}
