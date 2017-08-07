package ru.ppzhuk.game

data class Vector(val x: Double, val y: Double) {
    operator fun plus(other: Vector) = Vector(x + other.x, y + other.y)
    operator fun minus(other: Vector) = Vector(x - other.x, y - other.y)

    fun distance(other: Vector): Double {
        val xdiff = x - other.x
        val ydiff = y - other.y

        return Math.sqrt(xdiff*xdiff + (ydiff*ydiff))
    }

    companion object {
        @JvmStatic
        fun normalize(vec: Vector, maxVelocity: Double): Vector {
            val length = Vector(vec.x, vec.y).distance(Vector(0.0, 0.0))
            var x1 = vec.x * maxVelocity / length
            if (x1.isNaN()) x1 = 0.0
            var y1 = vec.y * maxVelocity / length
            if (y1.isNaN()) y1 = 0.0

            return Vector(x1, y1)
        }
    }
}