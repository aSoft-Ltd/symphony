package symphony

import kevlar.Action1
import koncurrent.Later

typealias FormInitialzer<P, R> = FormActionsBuilder<P, R>.() -> Action1<P, Later<R>>