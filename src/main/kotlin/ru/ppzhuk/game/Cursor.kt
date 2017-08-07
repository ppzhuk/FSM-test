package ru.ppzhuk.game

import java.awt.event.MouseEvent
import java.awt.event.MouseMotionAdapter

class Cursor : MouseMotionAdapter() {
    var position: Vector = Vector()

    override fun mouseMoved(e: MouseEvent) {
        position = Vector(e.x.toDouble(), e.y.toDouble())
    }
}