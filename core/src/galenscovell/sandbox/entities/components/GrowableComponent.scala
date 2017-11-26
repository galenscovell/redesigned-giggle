package galenscovell.sandbox.entities.components

import com.badlogic.ashley.core.Component
import galenscovell.sandbox.global.enums.Season


class GrowableComponent(val dayPlanted: Int,
                        val season: Season.Value,
                        val growthDays: Array[Int],
                        val regrows: Boolean) extends Component {
  private var lastGrowthDay: Int = dayPlanted


  def setLastUpdated(day: Int): Unit = {
    lastGrowthDay = day
  }

  def getDaysToNextStage(stageId: Int): Int = growthDays(stageId)

  def getLastUpdated: Int = lastGrowthDay
}
