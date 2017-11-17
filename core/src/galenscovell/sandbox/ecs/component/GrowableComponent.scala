package galenscovell.sandbox.ecs.component

import com.badlogic.ashley.core.Component
import galenscovell.sandbox.enums.Season


class GrowableComponent(val dayPlanted: Int,
                        val season: Season.Value,
                        val daysToBud: Int,
                        val daysToImmature: Int,
                        val daysToMature: Int) extends Component {
  var lastUpdatedDay: Int = dayPlanted
}
