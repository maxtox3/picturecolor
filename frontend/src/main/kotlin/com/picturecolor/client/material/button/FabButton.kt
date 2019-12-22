package com.picturecolor.client.material.button

import com.picturecolor.client.material.*
import org.w3c.dom.events.Event
import react.RBuilder
import react.RComponent
import react.RState
import styled.StyledHandler


@JsModule("@material-ui/core/Fab")
private external val fabModule: dynamic

@Suppress("UnsafeCastFromDynamic")
private val fabComponent: RComponent<MFabProps, RState> = fabModule.default

@Suppress("EnumEntryName")
enum class MFabVariant {
    round, extended
}

interface MFabProps : MButtonBaseProps {
    var disableFocusRipple: Boolean
    var href: String
}

var MFabProps.color by EnumPropToString(MColor.values())
var MFabProps.size by EnumPropToString(MButtonSize.values())
var MFabProps.variant by EnumPropToString(MFabVariant.values())


/**
 * FAB button that is round and has a convenience iconName.
 */
fun RBuilder.mFab(
        iconName: String,
        color: MColor = MColor.default,
        disabled: Boolean = false,
        onClick: ((Event) -> Unit)? = null,
        size: MButtonSize = MButtonSize.medium,
        hRefOptions: HRefOptions? = null,

        addAsChild: Boolean = true,
        className: String? = null,
        handler: StyledHandler<MFabProps>? = null) = createStyled(fabComponent, addAsChild) {
    attrs.color = color
    attrs.disabled = disabled
    hRefOptions?.let { setHRefTargetNoOpener(attrs, it) }
    onClick?.let { attrs.onClick = onClick }
    attrs.size = size
    attrs.variant = MFabVariant.round

    mIcon(iconName)

    setStyledPropsAndRunHandler(className, handler)
}

/**
 * FAB button with a caption which turns into an extended FAB type.
 */
fun RBuilder.mFab(
        iconName: String,
        caption: String,
        color: MColor = MColor.default,
        disabled: Boolean = false,
        onClick: ((Event) -> Unit)? = null,
        size: MButtonSize = MButtonSize.medium,
        hRefOptions: HRefOptions? = null,

        addAsChild: Boolean = true,
        className: String? = null,
        handler: StyledHandler<MFabProps>? = null) = createStyled(fabComponent, addAsChild) {
    attrs.color = color
    attrs.disabled = disabled
    hRefOptions?.let { setHRefTargetNoOpener(attrs, it) }
    onClick?.let { attrs.onClick = onClick }
    attrs.size = size
    attrs.variant = MFabVariant.extended

    mIcon(iconName)
    childList.add(caption)

    setStyledPropsAndRunHandler(className, handler)
}
