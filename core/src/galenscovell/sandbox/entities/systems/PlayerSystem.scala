package galenscovell.sandbox.entities.systems

import com.badlogic.ashley.core._
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Vector2
import galenscovell.sandbox.entities.components.{BodyComponent, StateComponent}
import galenscovell.sandbox.processing.input.ControllerHandler
import galenscovell.sandbox.global.Constants


class PlayerSystem(family: Family, controllerHandler: ControllerHandler) extends IteratingSystem(family) {
  private val bodyMapper: ComponentMapper[BodyComponent] = ComponentMapper.getFor(classOf[BodyComponent])
  private val stateMapper: ComponentMapper[StateComponent] = ComponentMapper.getFor(classOf[StateComponent])


  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    val bodyComponent: BodyComponent = bodyMapper.get(entity)
    val stateComponent: StateComponent = stateMapper.get(entity)

    val moveSpeed = if (controllerHandler.runPressed) Constants.RUN_SPEED else Constants.WALK_SPEED

    bodyComponent.body.setLinearVelocity(
      controllerHandler.leftAxis.x * moveSpeed,
      controllerHandler.leftAxis.y * moveSpeed
    )

    // Normalize diagonal movement
    val endVelocity: Vector2 = bodyComponent.body.getLinearVelocity
    if (endVelocity.len() > moveSpeed) {
      bodyComponent.body.setLinearVelocity(endVelocity.nor().scl(moveSpeed))
    }
  }
}