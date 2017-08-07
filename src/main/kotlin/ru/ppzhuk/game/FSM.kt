package ru.ppzhuk.game

import java.util.*

typealias FSMState = () -> Unit

class FSM(state: FSMState) {
    private val stateStack: Deque<FSMState> = LinkedList()

    init {
        stateStack.push(state)
    }

    fun getCurrentState(): FSMState = stateStack.last()

    fun update() = getCurrentState()()

    fun pushState(state: FSMState) {
        if (stateStack.size == 0 || getCurrentState() != state) {
            stateStack.addLast(state)
        }
    }

    fun popState(): FSMState = stateStack.removeLast()
}