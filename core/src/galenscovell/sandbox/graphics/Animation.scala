package galenscovell.sandbox.graphics

import com.badlogic.gdx.graphics.g2d.TextureRegion


class Animation(val frames: com.badlogic.gdx.utils.Array[AnimationFrame], val loop: Boolean) {
  private var done: Boolean = frames.size <= 1
  private var index: Int = 0

  private var currentFrame: AnimationFrame = _
  private var currentFrameTime: Float = _

  setFrame()


  def update(delta: Float): Unit = {
    if (done) return

    currentFrameTime -= delta

    if (currentFrameTime <= 0) {
      index += 1

      if (index > frames.size - 1) {
        if (loop) {
          index = 0
        } else {
          done = true
          return
        }
      }

      setFrame()
    }
  }

  private def setFrame(): Unit = {
    currentFrame = frames.get(index)
    currentFrameTime = currentFrame.length
  }

  def getFrame: TextureRegion = currentFrame.textureRegion
}
