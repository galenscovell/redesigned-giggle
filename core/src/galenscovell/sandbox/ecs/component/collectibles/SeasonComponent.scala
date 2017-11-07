package galenscovell.sandbox.ecs.component.collectibles

import com.badlogic.ashley.core.Component
import galenscovell.sandbox.util.Seasons


class SeasonComponent(s: Seasons.Value) extends Component {
  val season: Seasons.Value = s
}
