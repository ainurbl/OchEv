package baseclasses.dataclasses

import kotlin.math.sqrt

data class Vector(var x: Int = 0, var y: Int = 0) {
    val length: Float
        get() = sqrt(((x * x) + (y * y)).toFloat())

    constructor(beginPoint: Point, endPoint: Point) : this() {
        x = endPoint.x - beginPoint.x
        y = endPoint.y - beginPoint.y
    }
}

class VectorInteractor {
    fun scalarProduct(firstVector: Vector, secondVector: Vector): Int {
        return firstVector.x * secondVector.x + firstVector.y * secondVector.y
    }
}