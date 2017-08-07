package ru.ppzhuk.game

import ru.ppzhuk.ui.GameElement
import java.awt.Graphics
import java.awt.Image
import javax.imageio.ImageIO

// TODO: movement speed rewrite

class Mouse(x: Int, y: Int) : GameElement {
    private var position: Vector = Vector(x, y)
    private var velocity: Vector = Vector(0, 0)
    private val brain: FSM = FSM(this::findPoint1)

    val resource = ClassLoader.getSystemResourceAsStream("mouse.png")
    private val img: Image = ImageIO.read(
            resource
    )

    constructor() : this(150, 150)

    private fun findPoint1() {
        velocity = Vector(-1, 0)

        if (position.distance(Game.point1) < 10.0) {
            brain.setState(this::findPoint2)
        }
    }

    private fun findPoint2() {
        velocity = Vector(1, 0)
        if (position.distance(Game.point2) < 10.0) {
            brain.setState(this::findPoint1)
        }
    }

    private fun runAway() {
        TODO()
    }

    override fun redraw(g: Graphics) {
        g.drawImage(img,
                this.position.x - img.getWidth(null) / 2,
                this.position.y - img.getHeight(null) / 2, null
        )
    }

    override fun update() {
        brain.update()
        move()
    }

    fun move() {
        position += velocity
    }
}