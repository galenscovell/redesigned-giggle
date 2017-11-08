package galenscovell.sandbox.processing.input

import com.badlogic.gdx.controllers._
import com.badlogic.gdx.math.Vector2


class ControllerHandler extends ControllerAdapter {
  val leftAxis: Vector2 = new Vector2(0, 0)
  val rightAxis: Vector2 = new Vector2(0, 0)
  var runPressed: Boolean = false


  override def buttonUp(controller: Controller, buttonCode: Int): Boolean = {
    buttonCode match {
      case 0 => runPressed = false
      case 1 =>
      case 2 =>
      case 3 =>
      case _ =>
    }

    true
  }

  override def buttonDown(controller: Controller, buttonCode: Int): Boolean = {
    buttonCode match {
      case 0 => runPressed = true
      case 1 =>
      case 2 =>
      case 3 =>
      case _ =>
    }

    true
  }

  override def povMoved(controller: Controller, povCode: Int, value: PovDirection): Boolean = {
    true
  }

  override def axisMoved(controller: Controller, axisCode: Int, value: Float): Boolean = {
    // 1, 1 is bottom right, -1, -1 is upper left
    if (Math.abs(value) > 0.1) {
      axisCode match {
        case 0 => leftAxis.x = value  // left-vertical
        case 1 => leftAxis.y = -value   // left-horizontal
        case 2 => rightAxis.x = value // right-vertical
        case 3 => rightAxis.y = -value  // right-horizontal
      }
    } else {
      axisCode match {
        case 0 => leftAxis.x = 0
        case 1 => leftAxis.y = 0
        case 2 => rightAxis.x = 0
        case 3 => rightAxis.y = 0
      }
    }

    true
  }

  override def connected(controller: Controller): Unit = {
    println("Connected controller %s".format(controller.getName))
  }

  override def disconnected(controller: Controller): Unit = {
    println("Disconnected controller %s".format(controller.getName))
  }
}
