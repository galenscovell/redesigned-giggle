package galenscovell.sandbox.entities.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.{Animation, TextureRegion}


class AnimationComponent(val animationMap: Map[String, Animation[TextureRegion]]) extends Component {

}
