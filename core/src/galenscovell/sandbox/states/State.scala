package galenscovell.sandbox.states


trait State[StateComponent] {
  def enter(stateComponent: StateComponent): Unit

  def exit(stateComponent: StateComponent): Unit

  def update(delta: Float, stateComponent: StateComponent): Unit

  def getName: String
}
