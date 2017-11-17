package galenscovell.sandbox.ecs.system

import com.badlogic.ashley.core.{Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import galenscovell.sandbox.singletons.EntityManager


class RemovalSystem(family: Family) extends IteratingSystem(family) {
  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    EntityManager.getEngine.removeEntity(entity)
  }
}
