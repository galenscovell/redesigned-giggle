package galenscovell.sandbox.entities.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Sprite


class ItemComponent(val description: String,
                    val buyPrice: Int,
                    val sellPrice: Int,
                    val iconSprite: Sprite) extends Component {

}
