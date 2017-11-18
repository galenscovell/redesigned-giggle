package galenscovell.sandbox.entities.systems

import com.badlogic.ashley.core.{Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import galenscovell.sandbox.global.EntityManager


class RemovalSystem(family: Family) extends IteratingSystem(family) {
  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    EntityManager.getEngine.removeEntity(entity)
  }
}
