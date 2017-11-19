package galenscovell.sandbox.entities.systems

import com.badlogic.ashley.core._
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import galenscovell.sandbox.entities.components.{BodyComponent, StateComponent}
import galenscovell.sandbox.processing.input.ControllerHandler
import galenscovell.sandbox.global.Constants
import galenscovell.sandbox.global.enums.Direction
import galenscovell.sandbox.states.PlayerAgent


class PlayerSystem(family: Family, controllerHandler: ControllerHandler) extends IteratingSystem(family) {
  private val bodyMapper: ComponentMapper[BodyComponent] = ComponentMapper.getFor(classOf[BodyComponent])
  private val stateMapper: ComponentMapper[StateComponent] = ComponentMapper.getFor(classOf[StateComponent])


  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    val body: Body = bodyMapper.get(entity).body
    val stateComponent: StateComponent = stateMapper.get(entity)

    val moveSpeed = if (controllerHandler.runPressed) Constants.RUN_SPEED else Constants.WALK_SPEED

    body.setLinearVelocity(
      controllerHandler.leftAxis.x * moveSpeed,
      controllerHandler.leftAxis.y * moveSpeed
    )

    val bodyVelocity: Vector2 = body.getLinearVelocity
    if (bodyVelocity.isZero(0.05f)) {
      stateComponent.setState(PlayerAgent.DEFAULT)
    } else {
      stateComponent.setState(PlayerAgent.WALK)
    }

    if (Math.abs(bodyVelocity.x) > Math.abs(bodyVelocity.y)) {
      if (bodyVelocity.x > 0) {
        stateComponent.setDirection(Direction.RIGHT)
      } else {
        stateComponent.setDirection(Direction.LEFT)
      }
    } else if (Math.abs(bodyVelocity.y) > Math.abs(bodyVelocity.x)) {
      if (bodyVelocity.y > 0) {
        stateComponent.setDirection(Direction.UP)
      } else {
        stateComponent.setDirection(Direction.DOWN)
      }
    }

    // Normalize diagonal movement
    val endVelocity: Vector2 = body.getLinearVelocity
    if (endVelocity.len() > moveSpeed) {
      body.setLinearVelocity(endVelocity.nor().scl(moveSpeed))
    }
  }
}