package com.picturecolor.client.material.card

import com.picturecolor.client.material.createStyled
import com.picturecolor.client.material.setStyledPropsAndRunHandler
import react.RBuilder
import react.RComponent
import react.RState
import styled.StyledHandler
import styled.StyledProps


@JsModule("@material-ui/core/CardContent")
private external val cardContentModule: dynamic

@Suppress("UnsafeCastFromDynamic")
private val cardContentComponent : RComponent<MCardContentProps, RState> = cardContentModule.default

interface MCardContentProps : StyledProps {
    var component: String
}

fun RBuilder.mCardContent(className: String? = null,
                          handler: StyledHandler<MCardContentProps>? = null) = createStyled(cardContentComponent) {
    setStyledPropsAndRunHandler(className, handler)
}