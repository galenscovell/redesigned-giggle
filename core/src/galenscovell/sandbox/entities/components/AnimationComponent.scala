package galenscovell.sandbox.entities.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.{Animation, TextureRegion}
import com.badlogic.gdx.utils.IntMap


class AnimationComponent extends Component {
  val animationMap: IntMap[Animation[TextureRegion]] = new IntMap[Animation[TextureRegion]]()
}
