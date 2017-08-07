package ru.ppzhuk.game

import ru.ppzhuk.ui.GameElement
import java.awt.Graphics
import java.awt.geom.AffineTransform
import java.awt.image.AffineTransformOp
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

class Mouse(x: Double, y: Double) : GameElement {
    private var position: Vector = Vector(x, y)
    private var velocity: Vector = Vector(0.0, 0.0)
    private val brain: FSM = FSM(this::findPoint3)

    private val img: BufferedImage = ImageIO.read(
            ClassLoader.getSystemResourceAsStream("mouse.png")
    )

    companion object {
        @JvmStatic
        private val MAX_VELOCITY = 3.0
    }

    constructor() : this(150.0, 100.0)

    private fun findPoint1(): Unit = findPoint(Game.point1, this::findPoint2)
    private fun findPoint2(): Unit = findPoint(Game.point2, this::findPoint3)
    private fun findPoint3(): Unit = findPoint(Game.point3, this::findPoint1)

    private fun findPoint(point: Vector, secondState: () -> Unit) {
        velocity = Vector.normalize(getRelativeVector(point.x, point.y), MAX_VELOCITY)
        if (position.distance(point) < 10.0) {
            brain.state = secondState
        }
    }

    private fun runAway() {
        TODO()
    }

    override fun redraw(g: Graphics) {
        val imgCenterX = img.getWidth(null) / 2.0
        val imgCenterY = img.getHeight(null) / 2.0
        val rotateOperation = affineTransformOp(imgCenterX, imgCenterY)
        val x = this.position.x - imgCenterX
        val y = this.position.y - imgCenterY
        g.drawImage(rotateOperation.filter(img, null), x.toInt(), y.toInt(), null)
    }

    private fun affineTransformOp(imgCenterX: Double, imgCenterY: Double): AffineTransformOp {
        val tx = AffineTransform.getRotateInstance(getAngle(), imgCenterX, imgCenterY)
        return AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR)
    }

    private fun getAngle(): Double {
        val vec = when (brain.state) {
            this::findPoint1 -> getRelativeVector(Game.point1.x, Game.point1.y)
            this::findPoint2 -> getRelativeVector(Game.point2.x, Game.point2.y)
            this::findPoint3 -> getRelativeVector(Game.point3.x, Game.point3.y)
            else -> throw IllegalStateException("Unknown Mouse state")
        }
        val imgVec = Vector(0.0, 10.0)

        return Math.atan2(imgVec.x * vec.y - imgVec.y * vec.x, imgVec.x * vec.x + imgVec.y * vec.y)
    }

    private fun getRelativeVector(x: Double, y: Double) =
            Vector(x - position.x, y - position.y)

    override fun update() {
        brain.update()
        move()
    }

    fun move() {
        position += velocity
    }
}