package galenscovell.sandbox.processing

import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.Input.Keys


class KeyboardAndMouseHandler extends InputProcessor {
  val leftAxis: Vector2 = new Vector2(0, 0)


  override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
    false
  }

  override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
    false
  }

  override def keyUp(keycode: Int): Boolean = {
    false
  }

  override def scrolled(amount: Int): Boolean = {
    false
  }

  override def keyTyped(character: Char): Boolean = {
    false
  }

  override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = {
    false
  }

  override def keyDown(keyCode: Int): Boolean = {
    if (keyCode == Keys.LEFT) {
      leftAxis.x = -10
    }
    if (keyCode == Keys.RIGHT) {
      leftAxis.x = 10
    }
    false
  }

  override def mouseMoved(screenX: Int, screenY: Int): Boolean = {
    false
  }
}
