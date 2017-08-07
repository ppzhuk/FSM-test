package ru.ppzhuk.ui

import ru.ppzhuk.game.Game
import java.awt.Graphics
import javax.swing.JPanel

class GamePanel(private val game: Game) : JPanel() {

    init {
        addMouseMotionListener(game.cursor)
    }

    override fun paint(g: Graphics) {
        super.paint(g)

        game.update()
        game.redraw(g)
    }
}