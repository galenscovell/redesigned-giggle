package galenscovell.sandbox.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import galenscovell.sandbox.processing.BaseSteerable

class PlayerLocatorComponent(playerSteerable: BaseSteerable) extends Component {
  def getPlayerPosition: Vector2 = playerSteerable.getBody.getPosition
  def getPlayerSteerable: BaseSteerable = playerSteerable
}
