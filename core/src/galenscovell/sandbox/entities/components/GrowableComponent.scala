package galenscovell.sandbox.entities.components

import com.badlogic.ashley.core.Component
import galenscovell.sandbox.global.enums.Season


class GrowableComponent(val dayPlanted: Int,
                        val season: Season.Value,
                        val growthDays: Array[Int],
                        val regrows: Boolean) extends Component {
  private var currentStage: Int = 0
  private var lastGrowthDay: Int = dayPlanted


  def incrementStage(): Unit = {
    currentStage += 1
  }

  def decrementStage(): Unit = {
    currentStage -= 1
  }

  def setLastUpdated(day: Int): Unit = {
    lastGrowthDay = day
  }

  def getCurrentStage: Int = currentStage

  def getDaysToStage(stageId: Int): Int = growthDays(stageId)

  def getLastUpdated: Int = lastGrowthDay
}
