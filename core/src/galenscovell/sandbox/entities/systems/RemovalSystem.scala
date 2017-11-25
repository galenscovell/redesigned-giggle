package galenscovell.sandbox.entities.systems

import com.badlogic.ashley.core.{ComponentMapper, Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.physics.box2d.{Body, World}
import galenscovell.sandbox.entities.components.BodyComponent
import galenscovell.sandbox.global.EntityManager


class RemovalSystem(family: Family, world: World) extends IteratingSystem(family) {
  private val bodyMapper: ComponentMapper[BodyComponent] = ComponentMapper.getFor(classOf[BodyComponent])


  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    val body: Body = bodyMapper.get(entity).body

    world.destroyBody(body)

    EntityManager.getEngine.removeEntity(entity)
  }
}
