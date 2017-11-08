package galenscovell.sandbox.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Sprite
import galenscovell.sandbox.singletons.Resources


class SpriteComponent(name: String) extends Component {
  val sprite: Sprite = Resources.atlas.createSprite(name)
}
