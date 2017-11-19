package galenscovell.sandbox.entities.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion


class TextureComponent extends Component {
  val region: TextureRegion = new TextureRegion()
}
