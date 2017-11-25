package galenscovell.sandbox.entities.systems

import com.badlogic.ashley.core.{ComponentMapper, Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.physics.box2d._
import galenscovell.sandbox.entities.components.dynamic.InteractiveComponent
import galenscovell.sandbox.entities.components.{BodyComponent, GrowableComponent, StateComponent}
import galenscovell.sandbox.global.{Clock, EntityManager}
import galenscovell.sandbox.processing.input.ControllerHandler
import galenscovell.sandbox.states.CropAgent


class CropInteractionSystem(family: Family,
                            world: World,
                            controllerHandler: ControllerHandler) extends IteratingSystem(family) with ContactListener {
  private val bodyMapper: ComponentMapper[BodyComponent] = ComponentMapper.getFor(classOf[BodyComponent])
  private val growableMapper: ComponentMapper[GrowableComponent] = ComponentMapper.getFor(classOf[GrowableComponent])
  private val stateMapper: ComponentMapper[StateComponent] = ComponentMapper.getFor(classOf[StateComponent])

  world.setContactListener(this)

  // These are grabbed from the world contact listener methods
  private var collisionEntityA: Entity = _
  private var collisionEntityB: Entity = _


  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    if (collisionEntityB != null && collisionEntityA != null && entity == collisionEntityB) {
      val body: Body = bodyMapper.get(entity).body
      val otherBody: Body = bodyMapper.get(collisionEntityA).body

      val growComponent: GrowableComponent = growableMapper.get(entity)
      val stateComponent: StateComponent = stateMapper.get(entity)

      if (controllerHandler.selectPressed) {
        controllerHandler.selectPressed = false

        if (growComponent.regrows) {
          stateComponent.setState(CropAgent.STAGE4)
          growComponent.setLastUpdated(Clock.getDay)
          growComponent.decrementStage()
        } else {
          EntityManager.addRemovalComponent(entity)
        }

        entity.remove(classOf[InteractiveComponent])
      }
    }
  }


  /********************
    *      Box2D      *
    ********************/
  override def beginContact(contact: Contact): Unit = {
    collisionEntityA = contact.getFixtureA.getBody.getUserData.asInstanceOf[Entity]
    collisionEntityB = contact.getFixtureB.getBody.getUserData.asInstanceOf[Entity]
  }

  override def preSolve(contact: Contact, oldManifold: Manifold): Unit = {}

  override def postSolve(contact: Contact, impulse: ContactImpulse): Unit = {}

  override def endContact(contact: Contact): Unit = {}
}
