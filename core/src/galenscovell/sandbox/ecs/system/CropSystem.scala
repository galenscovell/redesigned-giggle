package galenscovell.sandbox.ecs.system

import com.badlogic.ashley.core.{ComponentMapper, Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import galenscovell.sandbox.ecs.component.{GrowableComponent, StateComponent}


class CropSystem(family: Family) extends IteratingSystem(family) {
  private val growableMapper: ComponentMapper[GrowableComponent] = ComponentMapper.getFor(classOf[GrowableComponent])
  private val stateMapper: ComponentMapper[StateComponent] = ComponentMapper.getFor(classOf[StateComponent])


  override def processEntity(entity: Entity, deltaTime: Float): Unit = {

  }
}
