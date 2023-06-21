package symphony

fun <O, F : Fields<O>> F.toInput(): Input<F> = Input(this)