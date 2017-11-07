package galenscovell.sandbox.ecs.component

import com.badlogic.ashley.core.Component


class SizeComponent(val w: Float, val h: Float) extends Component {
  val width: Float = w
  val height: Float = h
}
