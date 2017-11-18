package galenscovell.sandbox.entities.components

import com.badlogic.ashley.core.Component
import galenscovell.sandbox.states.State


class StateComponent(startState: State[StateComponent]) extends Component {
  private var previousState: State[StateComponent] = _
  private var currentState: State[StateComponent] = _

  private var stateTime: Float = _

  setState(startState)


  def update(delta: Float): Unit = {
    stateTime += delta
    currentState.update(delta, this)
  }

  def setState(newState: State[StateComponent]): Unit = {
    if (currentState == newState) return

    if (currentState != null) {
      previousState = currentState
      currentState.exit(this)
    }

    currentState = newState
    currentState.enter(this)

    resetStateTime()
  }

  def isInState(state: State[StateComponent]): Boolean = {
    currentState == state
  }

  def resetStateTime(): Unit = {
    stateTime = 0
  }

  def getCurrentState: State[StateComponent] = currentState

  def getPreviousState: State[StateComponent] = previousState

  def getStateTime: Float = stateTime
}
