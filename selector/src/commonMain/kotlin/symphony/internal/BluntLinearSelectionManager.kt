package symphony.internal

import cinematic.MutableLive
import cinematic.mutableLiveOf
import symphony.selected.LinearSelected
import symphony.selected.LinearSelectedNone
import symphony.LinearSelectionManager

class BluntLinearSelectionManager<T> private constructor() :  LinearSelectionManager<T> {

    companion object {
        val instance by lazy { BluntLinearSelectionManager<Nothing>() }
    }

    override val selected: MutableLive<LinearSelected<T>> = mutableLiveOf(LinearSelectedNone)
    override fun selectAllItemsInTheCurrentPage() = Unit

    override fun selectAllItemsInPage(page: Int)  = Unit

    override fun selectAllItemsInAllPages() = Unit

    override fun select(row: Int, page: Int) = Unit

    override fun select(row: Int)  = Unit

    override fun addSelection(row: Int, page: Int) = Unit

    override fun addSelection(row: Int)  = Unit

    override fun toggleSelectionOfRowInPage(row: Int, page: Int) = Unit

    override fun toggleSelectionOfRowInCurrentPage(row: Int) = Unit

    override fun toggleSelectionOfPage(page: Int) = Unit

    override fun toggleSelectionOfCurrentPage() = Unit

    override fun isPageSelectedWholly(page: Int): Boolean = false

    override fun isPageSelectedPartially(page: Int): Boolean  = false

    override fun isCurrentPageSelectedWholly(): Boolean = false

    override fun isCurrentPageSelectedPartially(): Boolean = false

    override fun isRowSelectedOnCurrentPage(row: Int): Boolean = false

    override fun isRowSelectedOnPage(row: Int, page: Int): Boolean = false

    override fun unSelectAllItemsInAllPages() = Unit

    override fun unSelectAllItemsInTheCurrentPage() = Unit

    override fun unSelectAllItemsInPage(page: Int) = Unit

    override fun unSelectRowInCurrentPage(row: Int) = Unit

    override fun unSelectRowInPage(row: Int, page: Int) = Unit

    override fun unSelect(item: T) = Unit

    override fun addSelection(obj: T) = Unit

    override fun select(obj: T) = Unit

}