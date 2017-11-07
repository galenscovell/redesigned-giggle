package galenscovell.sandbox.ecs.component.collectibles

import com.badlogic.ashley.core.Component


class BuyableComponent(c: Int) extends Component {
  val cost: Int = c
}
