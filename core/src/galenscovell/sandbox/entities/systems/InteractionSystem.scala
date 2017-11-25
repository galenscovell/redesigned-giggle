package galenscovell.sandbox.entities.systems

import com.badlogic.ashley.core.{ComponentMapper, Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.physics.box2d._
import galenscovell.sandbox.entities.components.BodyComponent
import galenscovell.sandbox.entities.components.dynamic.InteractiveComponent
import galenscovell.sandbox.global.enums.Interaction
import galenscovell.sandbox.processing.input.ControllerHandler


class InteractionSystem(family: Family,
                        world: World,
                        controllerHandler: ControllerHandler) extends IteratingSystem(family) with ContactListener {
  private val bodyMapper: ComponentMapper[BodyComponent] = ComponentMapper.getFor(classOf[BodyComponent])
  private val interactiveMapper: ComponentMapper[InteractiveComponent] = ComponentMapper.getFor(classOf[InteractiveComponent])

  world.setContactListener(this)

  // These are grabbed from the world contact listener methods
  private var collisionEntityA: Entity = _
  private var collisionEntityB: Entity = _


  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    if (collisionEntityB != null && collisionEntityA != null && entity == collisionEntityB) {
      val body: Body = bodyMapper.get(entity).body
      val otherBody: Body = bodyMapper.get(collisionEntityA).body

      if (controllerHandler.selectPressed) {
        interactiveMapper.get(entity).interactionType match {
          case Interaction.Collect =>
            println("Player collecting entity")
            entity.remove(classOf[InteractiveComponent])
          case Interaction.Talk =>
            println("Player talking with entity")
        }

        controllerHandler.selectPressed = false
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
