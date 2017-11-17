package galenscovell.sandbox.ecs.system

import com.badlogic.ashley.core.{ComponentMapper, Entity, Family}
import com.badlogic.ashley.systems.IntervalIteratingSystem
import galenscovell.sandbox.ecs.component.{GrowableComponent, StateComponent}
import galenscovell.sandbox.environment.Clock


class CropSystem(family: Family, val clock: Clock) extends IntervalIteratingSystem(family, 10) {
  private val growableMapper: ComponentMapper[GrowableComponent] = ComponentMapper.getFor(classOf[GrowableComponent])
  private val stateMapper: ComponentMapper[StateComponent] = ComponentMapper.getFor(classOf[StateComponent])


  override def processEntity(entity: Entity): Unit = {
    val growComponent: GrowableComponent = growableMapper.get(entity)
    val stateComponent: StateComponent = stateMapper.get(entity)

    val currentDay: Int = clock.getDay

    if (currentDay > growComponent.lastUpdatedDay) {
      println("Updating crop")
      growComponent.lastUpdatedDay = currentDay
    }
  }
}
