package com.picturecolor.client.material.card

import com.picturecolor.client.material.createStyled
import com.picturecolor.client.material.setStyledPropsAndRunHandler
import react.RBuilder
import react.RComponent
import react.RState
import styled.StyledHandler
import styled.StyledProps

@JsModule("@material-ui/core/CardMedia")
private external val cardMediaModule: dynamic

@Suppress("UnsafeCastFromDynamic")
private val cardMediaComponent: RComponent<MCardMediaProps, RState> = cardMediaModule.default

interface MCardMediaProps : StyledProps {
    var component: String
    var image: String
    var title: String
}

fun RBuilder.mCardMedia(image: String,
                        title: String = "",

                        className: String? = null,
                        handler: StyledHandler<MCardMediaProps>? = null) = createStyled(cardMediaComponent) {
    attrs.image = image
    attrs.title = title

    setStyledPropsAndRunHandler(className, handler)
}
