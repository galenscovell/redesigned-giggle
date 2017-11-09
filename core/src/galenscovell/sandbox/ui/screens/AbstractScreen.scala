package galenscovell.sandbox.ui.screens

import com.badlogic.gdx.graphics.{GL20, OrthographicCamera}
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.{Gdx, Screen}

import galenscovell.sandbox.Program
import galenscovell.sandbox.singletons.Constants


class AbstractScreen(root: Program) extends Screen {
  protected val interfaceCamera: OrthographicCamera =
    new OrthographicCamera(Gdx.graphics.getWidth, Gdx.graphics.getHeight)
  protected val interfaceViewport: FitViewport =
    new FitViewport(Constants.UI_X, Constants.UI_Y, interfaceCamera)
  protected var interfaceStage: Stage = _


  protected def create(): Unit = {
    interfaceStage = new Stage(interfaceViewport, root.interfaceSpriteBatch)
  }

  override def render(delta: Float): Unit = {
    Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    interfaceStage.act(delta)
    interfaceStage.draw()
  }

  override def show(): Unit = {
    create()
    Gdx.input.setInputProcessor(interfaceStage)
  }

  override def resize(width: Int, height: Int): Unit = {
    if (interfaceStage != null) {
      interfaceStage.getViewport.update(width, height, true)
    }
  }

  override def hide(): Unit = {
    Gdx.input.setInputProcessor(null)
  }

  override def dispose(): Unit = {
    if (interfaceStage != null) {
      interfaceStage.dispose()
    }
  }

  override def pause(): Unit =  {}

  override def resume(): Unit =  {}
}
