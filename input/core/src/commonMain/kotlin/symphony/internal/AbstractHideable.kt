package symphony.internal

import symphony.Visibility
import symphony.properties.Hideable

abstract class AbstractHideable : Hideable {
    override fun show(show: Boolean?) = setVisibility(
        if (show != false) Visibility.Visible else Visibility.Hidden
    )

    override fun hide(hide: Boolean?) = setVisibility(
        if (hide != false) Visibility.Hidden else Visibility.Visible
    )
}