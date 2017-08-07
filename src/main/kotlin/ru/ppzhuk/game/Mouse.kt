package ru.ppzhuk.game

import ru.ppzhuk.ui.GameElement
import java.awt.Graphics
import java.awt.geom.AffineTransform
import java.awt.image.AffineTransformOp
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

class Mouse(x: Double, y: Double) : GameElement {
    private var position: Vector = Vector(x, y)
    private var desiredVelocity: Vector = Vector(0.0, 0.0)
    private var steeringForce: Vector = Vector(0.0, 0.0)
    private var velocity: Vector = Vector(0.0, 0.0)
    private val brain: FSM = FSM(this::findPoint3)

    private val img: BufferedImage = ImageIO.read(
            ClassLoader.getSystemResourceAsStream("mouse.png")
    )

    companion object {
        @JvmStatic private val MAX_VELOCITY = 3.0
        @JvmStatic private val MAX_FORCE = 0.5
    }

    constructor() : this(150.0, 64.0)

    private fun findPoint1(): Unit = findPoint(Game.point1, this::findPoint2)
    private fun findPoint2(): Unit = findPoint(Game.point2, this::findPoint3)
    private fun findPoint3(): Unit = findPoint(Game.point3, this::findPoint1)

    private fun findPoint(point: Vector, secondState: () -> Unit) {
        desiredVelocity = Vector.normalize(getRelativeVector(point.x, point.y), MAX_VELOCITY)
        steeringForce = Vector.normalize(desiredVelocity - velocity, MAX_FORCE)

        val diff = desiredVelocity - velocity
        velocity = if (Math.abs(diff.x) < 0.5 && Math.abs(diff.y) < 0.5) {
            desiredVelocity
        } else {
            Vector.normalize(velocity + steeringForce, MAX_VELOCITY)
        }

        if (position.distance(point) < 10.0) {
            brain.popState()
            brain.pushState(secondState)
        }
    }

    private fun runAway() {
        TODO()
    }

    override fun redraw(g: Graphics) {
        val imgCenterX = img.getWidth(null) / 2.0
        val imgCenterY = img.getHeight(null) / 2.0
        val angle = getAngle()
        val rotateOperation = AffineTransformOp(
                AffineTransform.getRotateInstance(angle, imgCenterX, imgCenterY),
                AffineTransformOp.TYPE_BILINEAR
        )
        val x = this.position.x - imgCenterX
        val y = this.position.y - imgCenterY
        g.drawImage(rotateOperation.filter(img, null), x.toInt(), y.toInt(), null)
    }

    private fun getAngle(): Double {
        val vec = velocity
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