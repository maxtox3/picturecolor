package com.picturecolor.client.material.card

import com.picturecolor.client.material.createStyled
import com.picturecolor.client.material.setStyledPropsAndRunHandler
import react.RBuilder
import react.RComponent
import react.RState
import styled.StyledHandler
import styled.StyledProps


@JsModule("@material-ui/core/Card")
private external val cardModule: dynamic

@Suppress("UnsafeCastFromDynamic")
private val cardComponent : RComponent<MCardProps, RState> = cardModule.default

interface MCardProps : StyledProps {
    var raised: Boolean
}

fun RBuilder.mCard(raised: Boolean = false,
                   className: String? = null,
                   handler: StyledHandler<MCardProps>? = null) = createStyled(cardComponent) {
    attrs.raised = raised

    setStyledPropsAndRunHandler(className, handler)
}
