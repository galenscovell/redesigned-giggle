package galenscovell.sandbox.entities.components

import com.badlogic.ashley.core.Component
import galenscovell.sandbox.graphics.Animation


class AnimationComponent(val animationMap: Map[String, Animation]) extends Component {

}
