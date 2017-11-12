package galenscovell.sandbox.environment

import galenscovell.sandbox.enums.Season


class DateTime {
  private val minDay: Int = 1
  private val maxDay: Int = 28
  private val minHour: Int = 1
  private val maxHour: Int = 23
  private val minuteIncrement: Int = 15
  private val minMinute: Int = 0
  private val maxMinute: Int = 45

  private var season: Season.Value = Season.Spring
  private var day: Int = minDay
  private var hour: Int = minHour
  private var minute: Int = minMinute


  /********************
    *      Update     *
    ********************/
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
    if (season == Season.Spring) {
      season = Season.Summer
    } else if (season == Season.Summer) {
      season = Season.Fall
    } else if (season == Season.Fall) {
      season = Season.Winter
    } else {
      season = Season.Spring
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
    var zeroPaddedHour: String = hour.toString
    if (zeroPaddedHour.length == 1) {
      zeroPaddedHour = s"0$zeroPaddedHour"
    }

    var zeroPaddedMinute: String = minute.toString
    if (zeroPaddedMinute.length == 1) {
      zeroPaddedMinute = s"0$zeroPaddedMinute"
    }

    s"$zeroPaddedHour:$zeroPaddedMinute"
  }

  def getSeason: Season.Value = season

  def getDay: Int = day

  def getHour: Int = hour

  def getMinute: Int = minute
}
