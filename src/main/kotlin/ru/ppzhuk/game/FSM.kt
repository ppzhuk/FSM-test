package ru.ppzhuk.game

class FSM(
        private var currState: () -> Unit
) {
    fun setState(state: () -> Unit) {
        currState = state
    }

    fun update() = currState()
}