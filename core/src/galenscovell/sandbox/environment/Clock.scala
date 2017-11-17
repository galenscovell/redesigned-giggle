package galenscovell.sandbox.environment

import com.badlogic.gdx.scenes.scene2d.ui.Label
import galenscovell.sandbox.enums.Season


class Clock {
  private val minDay: Int = 1
  private val maxDay: Int = 28
  private val minHour: Int = 1
  private val maxHour: Int = 23
  private val minuteIncrement: Int = 10
  private val minMinute: Int = 0
  private val maxMinute: Int = 50

  private val clockStep: Float = 0.05f // 6f
  private var clockAccumulator: Float = 0

  private var season: Season.Value = Season.Spring
  private var day: Int = minDay
  private var hour: Int = minHour
  private var minute: Int = minMinute

  private var plantGrowthCheck: Boolean = false


  /********************
    *      Update     *
    ********************/
  def update(delta: Float, dateLabel: Label, timeLabel: Label): Unit = {
    val frameTime: Float = Math.min(delta, 0.25f)
    clockAccumulator += frameTime
    while (clockAccumulator > clockStep) {
      clockAccumulator -= clockStep

      updateClock()
      timeLabel.setText(getTimeStamp)
      dateLabel.setText(getDateStamp)
    }
  }

  def updateClock(): Unit = {
    if (hour == maxHour && minute == maxMinute) {
      updateDate()
      hour = minHour
      minute = minMinute
    } else if (minute == maxMinute) {
      hour += 1
      minute = minMinute
    } else {
      minute += minuteIncrement
    }
  }

  def updateDate(): Unit = {
    if (day == maxDay) {
      incrementSeason()
      day = minDay
    } else {
      day += 1
    }
  }

  private def incrementSeason(): Unit = {
    season match {
      case Season.Spring => season = Season.Summer
      case Season.Summer => season = Season.Fall
      case Season.Fall => season = Season.Winter
      case Season.Winter => season = Season.Spring
    }
  }

  /********************
    *       Set       *
    ********************/
  def setSeason(newSeason: Season.Value): Unit = {
    season = newSeason
  }

  def setDay(newDay: Int): Unit = {
    day = newDay
  }

  def setHour(newHour: Int): Unit = {
    hour = newHour
  }

  def setMinute(newMinute: Int): Unit = {
    minute = newMinute
  }

  /********************
    *       Get       *
    ********************/
  def getDateStamp: String = {
    s"$season $day/$maxDay"
  }

  def getTimeStamp: String = {
    val zeroPaddedHour: String =
      if (hour < 10) "0" + hour.toString
      else hour.toString

    val zeroPaddedMinute: String =
      if (minute < 10) "0" + minute.toString
      else minute.toString

    s"$zeroPaddedHour:$zeroPaddedMinute"
  }

  def getSeason: Season.Value = season

  def getDay: Int = day

  def getHour: Int = hour

  def getMinute: Int = minute
}
