package com.example.ochev.viewclasses.buttonshandler

import android.view.View
import com.example.ochev.baseclasses.editors.FigureEditor
import com.example.ochev.viewclasses.drawers.GraphDrawer

class ButtonsHandler (
    val buttonsContainer: ButtonsContainer,
    private val graphDrawer: GraphDrawer
) {

    init{
        buttonsContainer.clearButton.setOnClickListener {
            exitEditing()
            graphDrawer.graphEditor.clear()
            graphDrawer.invalidate()
        }

        buttonsContainer.undoButton.setOnClickListener {
            exitEditing()
            graphDrawer.graphEditor.revertChange()
            graphDrawer.invalidate()
        }

        buttonsContainer.forwardButton.setOnClickListener {
            exitEditing()
            graphDrawer.graphEditor.undoRevertChange()
            graphDrawer.invalidate()
        }

        buttonsContainer.saveButton.setOnClickListener {
            //TODO()
        }
    }

    fun showDeleteButton() {
        buttonsContainer.deleteButton.visibility = View.VISIBLE
    }

    fun enterEditing(figureEditor: FigureEditor) {
        showDeleteButton()
        buttonsContainer.deleteButton.setOnClickListener {
            figureEditor.graphEditor.deleteFigure(figureEditor.currentFigureState)
            exitEditing()
            graphDrawer.invalidate()
        }
    }

    fun exitEditing() {
        buttonsContainer.deleteButton.visibility = View.INVISIBLE
    }

    fun disableAll() {
        buttonsContainer.forwardButton.isClickable = false
        buttonsContainer.undoButton.isClickable = false
        buttonsContainer.clearButton.isClickable = false
        buttonsContainer.deleteButton.isClickable = false
        buttonsContainer.saveButton.isClickable = false
    }

    fun enableAll() {
        buttonsContainer.forwardButton.isClickable = true
        buttonsContainer.undoButton.isClickable = true
        buttonsContainer.clearButton.isClickable = true
        buttonsContainer.deleteButton.isClickable = true
        buttonsContainer.saveButton.isClickable = true
    }

}