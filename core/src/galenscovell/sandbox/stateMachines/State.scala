package galenscovell.sandbox.stateMachines


trait State[StateComponent] {
  def enter(stateComponent: StateComponent): Unit

  def exit(stateComponent: StateComponent): Unit

  def update(stateComponent: StateComponent): Unit

  def getFrameRatio: Float

  def getName: String
}
