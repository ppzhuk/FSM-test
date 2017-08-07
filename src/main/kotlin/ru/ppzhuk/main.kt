package ru.ppzhuk

import ru.ppzhuk.game.Game
import ru.ppzhuk.ui.GamePanel
import javax.swing.JFrame
import javax.swing.Timer
import javax.swing.WindowConstants

val TIMER_UPDATE_RATE = 45
val WINDOW_WIDTH = 600
val WINDOW_HEIGHT = 600

fun main(args: Array<String>) {
    val gui = createGUI(Game)
    Timer(1000 / TIMER_UPDATE_RATE) {
        gui.repaint()
    }.start()
}

private fun createGUI(game: Game): JFrame =
        with(JFrame()) {
            title = "FSM test"
            defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
            setSize(WINDOW_WIDTH, WINDOW_HEIGHT)
            isResizable = false

            add(GamePanel(game))

            setLocationRelativeTo(null)
            isVisible = true
            this
        }