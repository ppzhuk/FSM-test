package ru.ppzhuk.game

import ru.ppzhuk.ui.GameElement
import java.awt.Graphics

object Game : GameElement {
    val point1: Vector = Vector(50, 150)
    val point2: Vector = Vector(250, 150)
    val mouse: Mouse = Mouse()

    override fun update() {
        mouse.update()
    }

    override fun redraw(g: Graphics) {
        mouse.redraw(g)
        g.drawOval(point1.x, point1.y, 3, 3)
        g.drawOval(point2.x, point2.y, 3, 3)
    }
}