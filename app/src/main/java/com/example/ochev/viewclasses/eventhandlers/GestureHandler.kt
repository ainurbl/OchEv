package com.example.ochev.viewclasses.eventhandlers

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import com.example.ochev.R
import com.example.ochev.baseclasses.dataclasses.Point
import com.example.ochev.baseclasses.editors.vertexeditor.VertexFigureEditor
import com.example.ochev.ml.Classifier
import com.example.ochev.viewclasses.GraphDrawer
import com.example.ochev.viewclasses.StrokeDrawer
import com.example.ochev.viewclasses.buttonshandler.ButtonsHandler

abstract class GestureEventHandler(
    private val strokeDrawer: StrokeDrawer,
    private val drawGraphView: GraphDrawer,
    private val classifier: Classifier
) {
    abstract fun handle(gesture: Gesture, event: MotionEvent)
}

class GestureHandler(
    private val strokeDrawer: StrokeDrawer,
    private val graphDrawer: GraphDrawer,
    private val buttonsHandler: ButtonsHandler,
    private val classifier: Classifier
) {

    private var gestureEventHandler: GestureEventHandler? = null

    private var currentFigureEditor: VertexFigureEditor? = null

    fun handle(gesture: Gesture, event: MotionEvent) {
        if (gestureEventHandler == null) {
            gestureEventHandler = chooseHandler(gesture, event)
        }

        if (gestureEventHandler != null) gestureEventHandler!!.handle(gesture, event)

        if (gesture.state == GestureState.END) gestureEventHandler = null

        Log.d("Gestures", gesture.toString())
    }

    private fun chooseHandler(gesture: Gesture, event: MotionEvent): GestureEventHandler? {

        if (gesture.type == GestureType.SCROLL) return ScrollingEventHandler(
            strokeDrawer,
            graphDrawer,
            buttonsHandler,
            classifier
        )

        currentFigureEditor = graphDrawer.graphView.graphEditor.getFigureEditorByTouch(Point(event))

        if (currentFigureEditor == null) {
            if (gesture.type == GestureType.MOVE) return DrawingEventHandler(
                strokeDrawer,
                graphDrawer,
                buttonsHandler,
                classifier
            )
        } else {
            return EditingEventHandler(
                strokeDrawer, graphDrawer,
                buttonsHandler, classifier,
                currentFigureEditor!!
            )
        }

        return null
    }
}