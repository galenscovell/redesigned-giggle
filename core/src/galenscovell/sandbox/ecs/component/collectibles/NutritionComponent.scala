package galenscovell.sandbox.ecs.component.collectibles

import com.badlogic.ashley.core.Component


class NutritionComponent(n: Int) extends Component {
  val nutrition: Int = n
}
