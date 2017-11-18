package galenscovell.sandbox.entities.systems

import com.badlogic.ashley.core.{ComponentMapper, Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import galenscovell.sandbox.entities.components.dynamic.DayPassedComponent
import galenscovell.sandbox.entities.components.{GrowableComponent, StateComponent}
import galenscovell.sandbox.states.CropAgent


class CropStateSystem(family: Family) extends IteratingSystem(family) {
  private val dayPassedMapper: ComponentMapper[DayPassedComponent] = ComponentMapper.getFor(classOf[DayPassedComponent])
  private val growableMapper: ComponentMapper[GrowableComponent] = ComponentMapper.getFor(classOf[GrowableComponent])
  private val stateMapper: ComponentMapper[StateComponent] = ComponentMapper.getFor(classOf[StateComponent])


  override def processEntity(entity: Entity, delta: Float): Unit = {
    val dayPassedComponent: DayPassedComponent = dayPassedMapper.get(entity)
    val growComponent: GrowableComponent = growableMapper.get(entity)
    val stateComponent: StateComponent = stateMapper.get(entity)

    // Find days that have passed since the date of planting
    val dayDiff: Int = dayPassedComponent.day - growComponent.dayPlanted

    stateComponent.getCurrentState match {
      case CropAgent.SEED => if (dayDiff == growComponent.daysToSprout) stateComponent.setState(CropAgent.SPROUT)
      case CropAgent.SPROUT => if (dayDiff == growComponent.daysToImmature) stateComponent.setState(CropAgent.IMMATURE)
      case CropAgent.IMMATURE => if (dayDiff == growComponent.daysToMature) stateComponent.setState(CropAgent.MATURE)
      case CropAgent.MATURE => println("Crop is ready for harvest")
    }

    // Remove the dayPassedComponent since we only want to check crops once each day
    entity.remove(classOf[DayPassedComponent])
  }
}
