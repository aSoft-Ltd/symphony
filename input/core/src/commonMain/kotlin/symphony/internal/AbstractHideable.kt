package symphony.internal

import symphony.Visibilities
import symphony.Visibility
import symphony.properties.Hideable

abstract class AbstractHideable : Hideable {
    override fun show(show: Boolean?) = setVisibility(
        if (show != false) Visibilities.Visible else Visibilities.Hidden
    )

    override fun hide(hide: Boolean?) = setVisibility(
        if (hide != false) Visibilities.Hidden else Visibilities.Visible
    )
}