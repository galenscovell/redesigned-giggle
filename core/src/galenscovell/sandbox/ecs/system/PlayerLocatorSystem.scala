package galenscovell.sandbox.ecs.system

import com.badlogic.ashley.core._
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Vector2
import galenscovell.sandbox.ecs.component.{BodyComponent, PlayerLocatorComponent}


class PlayerLocatorSystem(family: Family) extends IteratingSystem(family) {
  private val bodyMapper: ComponentMapper[BodyComponent] =
    ComponentMapper.getFor(classOf[BodyComponent])
  private val playerLocatorMapper: ComponentMapper[PlayerLocatorComponent] =
    ComponentMapper.getFor(classOf[PlayerLocatorComponent])


  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    val bodyComponent: BodyComponent = bodyMapper.get(entity)
    val playerLocatorComponent: PlayerLocatorComponent = playerLocatorMapper.get(entity)

    val currentPosition: Vector2 = bodyComponent.body.getPosition
    val currentPlayerPosition: Vector2 = playerLocatorComponent.getPlayerPosition
  }
}
