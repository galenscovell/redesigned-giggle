package galenscovell.sandbox.ecs.component

import com.badlogic.ashley.core.Component
import galenscovell.sandbox.enums.Season


class GrowableComponent(val days: Int, val season: Season.Value) extends Component {

}
