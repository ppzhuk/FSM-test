package ru.ppzhuk.game

import ru.ppzhuk.ui.GameElement
import java.awt.Graphics

object Game : GameElement {
    val point1: Vector = Vector(50.0, 150.0)
    val point2: Vector = Vector(250.0, 150.0)
    val point3: Vector = Vector(150.0, 50.0)
    val mouse: Mouse = Mouse()

    override fun update() {
        mouse.update()
    }

    override fun redraw(g: Graphics) {
        mouse.redraw(g)
        g.drawOval(point1.x.toInt(), point1.y.toInt(), 3, 3)
        g.drawOval(point2.x.toInt(), point2.y.toInt(), 3, 3)
        g.drawOval(point3.x.toInt(), point3.y.toInt(), 3, 3)
    }
}