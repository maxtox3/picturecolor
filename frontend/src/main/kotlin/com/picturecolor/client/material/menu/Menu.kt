package com.picturecolor.client.material.menu

import com.picturecolor.client.material.*
import com.picturecolor.client.material.transitions.AutoTransitionDuration
import com.picturecolor.client.material.transitions.TransitionComponent
import com.picturecolor.client.material.transitions.TransitionDurationWithAuto
import org.w3c.dom.Node
import org.w3c.dom.events.Event
import react.RBuilder
import react.RComponent
import react.RState
import styled.StyledHandler


@JsModule("@material-ui/core/Menu")
private external val menuModule: dynamic

@Suppress("UnsafeCastFromDynamic")
private val menuComponent: RComponent<MMenuProps, RState>  = menuModule.default

@Suppress("EnumEntryName")
enum class MenuOnCloseReason {
    escapeKeyDown, backdropClick, tabKeyDown
}

enum class MMenuVariant {
    menu, selectedMenu
}

interface MMenuProps : MPopoverProps {
    var autoFocus: Boolean

    @JsName("MenuListProps")
    var menuListProps: MMenuListProps

    @JsName("PopoverClasses")
    var popoverClasses: String
    var value: Any
}
var MMenuProps.variant by EnumPropToString(MMenuVariant.values())
var MMenuProps.onClose by OnClosePropWithReasonDelegate(MenuOnCloseReason.values())


fun RBuilder.mMenu(
        open: Boolean,
        anchorElement: Node? = null,
        onClose: ((Event, MenuOnCloseReason) -> Unit)? = null,
        autoFocus: Boolean = true,
        menuListProps: MMenuListProps? = null,
        popoverClasses: String? = null,
        transitionComponent: TransitionComponent? = null,
        transitionDuration: TransitionDurationWithAuto = AutoTransitionDuration(),
        variant: MMenuVariant = MMenuVariant.selectedMenu,

        className: String? = null,
        handler: StyledHandler<MMenuProps>) = createStyled(menuComponent) {
    anchorElement?.let { attrs.anchorEl = anchorElement }
    attrs.autoFocus = autoFocus
    menuListProps?.let { attrs.menuListProps = menuListProps }
    attrs.onClose = onClose
    attrs.open = open
    popoverClasses?.let { attrs.popoverClasses = popoverClasses }
    attrs.transitionComponent = transitionComponent
    attrs.transitionDuration = transitionDuration
    attrs.variant = variant

    setStyledPropsAndRunHandler(className, handler)
}

