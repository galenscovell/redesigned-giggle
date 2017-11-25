package galenscovell.sandbox.entities.systems

import com.badlogic.ashley.core.{ComponentMapper, Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import galenscovell.sandbox.entities.components.dynamic.DayPassedComponent
import galenscovell.sandbox.entities.components.{GrowableComponent, StateComponent}
import galenscovell.sandbox.global.EntityManager
import galenscovell.sandbox.states.CropAgent


class CropStateSystem(family: Family) extends IteratingSystem(family) {
  private val dayPassedMapper: ComponentMapper[DayPassedComponent] = ComponentMapper.getFor(classOf[DayPassedComponent])
  private val growableMapper: ComponentMapper[GrowableComponent] = ComponentMapper.getFor(classOf[GrowableComponent])
  private val stateMapper: ComponentMapper[StateComponent] = ComponentMapper.getFor(classOf[StateComponent])


  override def processEntity(entity: Entity, delta: Float): Unit = {
    val dayPassedComponent: DayPassedComponent = dayPassedMapper.get(entity)
    val growComponent: GrowableComponent = growableMapper.get(entity)
    val stateComponent: StateComponent = stateMapper.get(entity)

    if (stateComponent.getCurrentState != CropAgent.STAGE5) {
      // Find days that have passed since the last day of growth
      val dayDiff: Int = dayPassedComponent.day - growComponent.getLastUpdated

      if (dayDiff == growComponent.getDaysToStage(stateComponent.getCurrentState.getId)) {
        stateComponent.getCurrentState match {
          case CropAgent.STAGE0 => stateComponent.setState(CropAgent.STAGE1)
          case CropAgent.STAGE1 => stateComponent.setState(CropAgent.STAGE2)
          case CropAgent.STAGE2 => stateComponent.setState(CropAgent.STAGE3)
          case CropAgent.STAGE3 => stateComponent.setState(CropAgent.STAGE4)
          case CropAgent.STAGE4 => stateComponent.setState(CropAgent.STAGE5)
        }

        growComponent.setLastUpdated(dayPassedComponent.day)
        growComponent.incrementStage()
      }
    } else {
      // Crop in Stage6 is now able to be interacted with by Player
      EntityManager.addInteractiveComponent(entity)
    }

    // Remove the dayPassedComponent since we only want to check crops once each day
    entity.remove(classOf[DayPassedComponent])
  }
}
