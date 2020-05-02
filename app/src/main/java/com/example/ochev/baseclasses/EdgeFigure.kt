package com.example.ochev.baseclasses

import com.example.ochev.baseclasses.dataclasses.Point


// Figure that connects information blocks

abstract class EdgeFigure : Figure() {
    abstract fun getDistanceToPoint(point: Point): Float
}