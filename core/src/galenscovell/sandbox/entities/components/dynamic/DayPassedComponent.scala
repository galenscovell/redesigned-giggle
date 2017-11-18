package galenscovell.sandbox.entities.components.dynamic

import com.badlogic.ashley.core.Component
import galenscovell.sandbox.global.enums.Season


class DayPassedComponent(val season: Season.Value, val day: Int) extends Component {
  // This component is added to entities dynamically when a day has passed
  // It is removed by the system immediately after processing
  // Eg. all crops need to now check for growth
}
