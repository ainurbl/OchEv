package com.example.ochev.viewclasses

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.ochev.baseclasses.EdgeFigure
import com.example.ochev.baseclasses.Figure
import com.example.ochev.baseclasses.VertexFigure
import com.example.ochev.baseclasses.dataclasses.Graph
import com.example.ochev.baseclasses.edgefigures.Line
import com.example.ochev.baseclasses.vertexfigures.Circle
import com.example.ochev.baseclasses.vertexfigures.Rectangle
import com.example.ochev.baseclasses.vertexfigures.Triangle

class DrawGraphView(
    context: Context?,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    val graph = Graph()
    val drawGraphInteractor = DrawGraphInteractor()
    var graphPos = Point(0, 0)

    fun clear() {
        graph.vertexes.clear()
        graph.edges.clear()
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawColor(Color.rgb(255, 0 ,250))
        for (figure in graph.figuresSortedByHeights) {
            drawGraphInteractor.draw(figure, canvas, graphPos)
        }
    }
}

class FigureStyle {
    val fontPaint = Paint()
    val circuitPaint = Paint()
    val fillPaint = Paint()
}

abstract class Drawer {
    var currentStyle = 0
    val styles: MutableList<FigureStyle> = ArrayList(3)

    init {
        styles.add(FigureStyle())
        styles.add(FigureStyle())
        styles.add(FigureStyle())
    }

    fun setFontWidth(width: Float) {
        for (style in styles) {
            style.fontPaint.strokeWidth = width
        }
    }

    fun setCircuitWidth(width: Float) {
        for (style in styles) {
            style.circuitPaint.strokeWidth = width
        }
    }

    fun setFillWidth(width: Float) {
        for (style in styles) {
            style.fillPaint.strokeWidth = width
        }
    }

    abstract fun draw(figure: Figure, canvas: Canvas?, graphPos: Point);
}

class CircleDrawer : Drawer() {

    init {
        /*
            default style of circles
         */
        styles[DrawingMode.DEFAULT.ordinal].fillPaint.style = Paint.Style.FILL
        styles[DrawingMode.DEFAULT.ordinal].fillPaint.strokeWidth = 0f
        styles[DrawingMode.DEFAULT.ordinal].fillPaint.color = Color.rgb(232, 146, 253)
        styles[DrawingMode.DEFAULT.ordinal].circuitPaint.style = Paint.Style.STROKE
        styles[DrawingMode.DEFAULT.ordinal].circuitPaint.strokeWidth = 10f
        styles[DrawingMode.DEFAULT.ordinal].circuitPaint.color = Color.BLACK
        /*
            editing style of circles
         */
        styles[DrawingMode.EDIT.ordinal].fillPaint.style = Paint.Style.FILL
        styles[DrawingMode.EDIT.ordinal].fillPaint.strokeWidth = 0f
        styles[DrawingMode.EDIT.ordinal].fillPaint.color = Color.rgb(195, 0, 251)
        styles[DrawingMode.EDIT.ordinal].circuitPaint.style = Paint.Style.STROKE
        styles[DrawingMode.EDIT.ordinal].circuitPaint.strokeWidth = 10f
        styles[DrawingMode.EDIT.ordinal].circuitPaint.color = Color.BLACK
    }

    override fun draw(figure: Figure, canvas: Canvas?, graphPos: Point) {

        figure as Circle

        canvas?.drawCircle(
            figure.center.x.toFloat(),
            figure.center.y.toFloat(),
            figure.radius.toFloat(),
            styles[currentStyle].fillPaint
        )
        canvas?.drawCircle(
            figure.center.x.toFloat(),
            figure.center.y.toFloat(),
            figure.radius.toFloat(),
            styles[currentStyle].circuitPaint
        )
    }
}

class RectangleDrawer : Drawer() {

    init {
        /*
            default style of rectangles
         */
        styles[DrawingMode.DEFAULT.ordinal].fillPaint.style = Paint.Style.FILL
        styles[DrawingMode.DEFAULT.ordinal].fillPaint.strokeWidth = 0f
        styles[DrawingMode.DEFAULT.ordinal].fillPaint.color = Color.rgb(232, 146, 253)
        styles[DrawingMode.DEFAULT.ordinal].circuitPaint.style = Paint.Style.STROKE
        styles[DrawingMode.DEFAULT.ordinal].circuitPaint.strokeWidth = 10f
        styles[DrawingMode.DEFAULT.ordinal].circuitPaint.color = Color.BLACK
        /*
            editing style of rectangles
         */
        styles[DrawingMode.EDIT.ordinal].fillPaint.style = Paint.Style.FILL
        styles[DrawingMode.EDIT.ordinal].fillPaint.strokeWidth = 0f
        styles[DrawingMode.EDIT.ordinal].fillPaint.color = Color.rgb(195, 0, 251)
        styles[DrawingMode.EDIT.ordinal].circuitPaint.style = Paint.Style.STROKE
        styles[DrawingMode.EDIT.ordinal].circuitPaint.strokeWidth = 10f
        styles[DrawingMode.EDIT.ordinal].circuitPaint.color =  Color.BLACK

    }

    override fun draw(figure: Figure, canvas: Canvas?, graphPos: Point) {
        figure as Rectangle
        canvas?.drawRect(
            figure.leftDownCorner.x.toFloat(),
            figure.leftDownCorner.y.toFloat(),
            figure.rightUpCorner.x.toFloat(),
            figure.rightUpCorner.y.toFloat(),
            styles[currentStyle].fillPaint
        )
        canvas?.drawRect(
            figure.leftDownCorner.x.toFloat(),
            figure.leftDownCorner.y.toFloat(),
            figure.rightUpCorner.x.toFloat(),
            figure.rightUpCorner.y.toFloat(),
            styles[currentStyle].circuitPaint
        )
    }
}

class TriangleDrawer : Drawer() {

    init {
        /*
            default style of triangels
         */
        styles[DrawingMode.DEFAULT.ordinal].fillPaint.style = Paint.Style.FILL
        styles[DrawingMode.DEFAULT.ordinal].fillPaint.strokeWidth = 0f
        styles[DrawingMode.DEFAULT.ordinal].fillPaint.color = Color.WHITE
        styles[DrawingMode.DEFAULT.ordinal].circuitPaint.style = Paint.Style.STROKE
        styles[DrawingMode.DEFAULT.ordinal].circuitPaint.strokeWidth = 10f
        styles[DrawingMode.DEFAULT.ordinal].circuitPaint.color = Color.BLACK
        /*
            editing style of triangles
         */
        styles[DrawingMode.EDIT.ordinal].fillPaint.style = Paint.Style.FILL
        styles[DrawingMode.EDIT.ordinal].fillPaint.strokeWidth = 0f
        styles[DrawingMode.EDIT.ordinal].fillPaint.color = Color.WHITE
        styles[DrawingMode.EDIT.ordinal].circuitPaint.style = Paint.Style.STROKE
        styles[DrawingMode.EDIT.ordinal].circuitPaint.strokeWidth = 10f
        styles[DrawingMode.EDIT.ordinal].circuitPaint.color =  Color.BLUE
    }


    override fun draw(figure: Figure, canvas: Canvas?, graphPos: Point) {

        figure as Triangle

        val path = Path()
        path.moveTo(figure.pointA.x.toFloat(), figure.pointA.y.toFloat())
        path.lineTo(figure.pointB.x.toFloat(), figure.pointB.y.toFloat())
        path.lineTo(figure.pointC.x.toFloat(), figure.pointC.y.toFloat())
        path.close()
        canvas?.drawPath(path, styles[currentStyle].fillPaint)
        canvas?.drawPath(path, styles[currentStyle].circuitPaint)
    }
}

class LineDrawer : Drawer() {

    init {
        /*
            default style of lines
         */
        styles[DrawingMode.DEFAULT.ordinal].circuitPaint.style = Paint.Style.STROKE
        styles[DrawingMode.DEFAULT.ordinal].circuitPaint.color = Color.BLACK
        styles[DrawingMode.DEFAULT.ordinal].circuitPaint.strokeWidth = 10f
        /*
            editing style of lines
         */
        styles[DrawingMode.EDIT.ordinal].circuitPaint.style = Paint.Style.STROKE
        styles[DrawingMode.EDIT.ordinal].circuitPaint.color = Color.rgb(195, 0, 251)
        styles[DrawingMode.EDIT.ordinal].circuitPaint.strokeWidth = 10f

    }

    override fun draw(figure: Figure, canvas: Canvas?, graphPos: Point) {

        figure as Line

        val path = Path()
        val from = figure.beginFigure.center
        val to = figure.endFigure.center
        path.moveTo(from.x.toFloat(), from.y.toFloat())
        path.lineTo(to.x.toFloat(), to.y.toFloat())
        canvas?.drawPath(path, styles[currentStyle].circuitPaint)
    }
}

class DrawGraphInteractor {
    val circleDrawer = CircleDrawer()
    val rectangleDrawer = RectangleDrawer()
    val triangleDrawer = TriangleDrawer()
    val lineDrawer = LineDrawer()

    fun draw(figure: Figure, canvas: Canvas?, graphPos: Point = Point(0, 0)) {

        when (figure) {
            is Circle -> {
                circleDrawer.currentStyle = figure.drawingInformation.drawingMode.ordinal
                circleDrawer.draw(figure, canvas, graphPos)
            }
            is Rectangle -> {
                rectangleDrawer.currentStyle = figure.drawingInformation.drawingMode.ordinal
                rectangleDrawer.draw(figure, canvas, graphPos)
            }
            is Triangle -> {
                triangleDrawer.currentStyle = figure.drawingInformation.drawingMode.ordinal
                triangleDrawer.draw(figure, canvas, graphPos)
            }
            is Line -> {
                lineDrawer.currentStyle = figure.drawingInformation.drawingMode.ordinal
                lineDrawer.draw(figure, canvas, graphPos)
            }
        }
    }

}

