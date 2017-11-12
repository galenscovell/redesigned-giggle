package galenscovell.sandbox.ecs.system

import com.badlogic.ashley.core.{Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem


class AnimalSystem(family: Family) extends IteratingSystem(family) {

  override def processEntity(entity: Entity, deltaTime: Float): Unit = {

  }
}
