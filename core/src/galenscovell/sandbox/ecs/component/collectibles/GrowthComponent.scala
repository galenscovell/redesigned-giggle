package galenscovell.sandbox.ecs.component.collectibles

import com.badlogic.ashley.core.Component


class GrowthComponent(days: Int) extends Component {
  val daysToGrow: Int = days
}
