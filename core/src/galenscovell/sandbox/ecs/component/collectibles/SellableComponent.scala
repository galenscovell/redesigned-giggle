package galenscovell.sandbox.ecs.component.collectibles

import com.badlogic.ashley.core.Component


class SellableComponent(v: Int) extends Component {
  val sellValue: Int = v
}
