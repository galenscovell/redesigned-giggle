package galenscovell.sandbox.entities.systems

import com.badlogic.ashley.core.{Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem


class AnimalStateSystem(family: Family) extends IteratingSystem(family) {

  override def processEntity(entity: Entity, deltaTime: Float): Unit = {

  }
}
