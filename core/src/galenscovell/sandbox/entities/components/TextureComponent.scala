package galenscovell.sandbox.entities.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion
import galenscovell.sandbox.global.Resources


class TextureComponent(name: String) extends Component {
  var region: TextureRegion = Resources.atlas.findRegion(name)
}
