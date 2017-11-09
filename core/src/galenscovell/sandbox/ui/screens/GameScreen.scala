package galenscovell.sandbox.ui.screens

import aurelienribon.tweenengine.TweenManager
import com.badlogic.gdx.{Gdx, InputMultiplexer}
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.{GL20, OrthographicCamera}
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.{Label, Table}
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FitViewport
import galenscovell.sandbox.Program
import galenscovell.sandbox.processing.input.ControllerHandler
import galenscovell.sandbox.singletons.{Constants, Resources}
import galenscovell.sandbox.ui.component.EntityStage


class GameScreen(root: Program) extends AbstractScreen(root) {
  private var entityStage: EntityStage = _
  private val tweenManager: TweenManager = new TweenManager

  private val input: InputMultiplexer = new InputMultiplexer
  private val controllerHandler: ControllerHandler = new ControllerHandler
  private var paused: Boolean = false

  private val fpsLabel: Label = new Label("FPS", Resources.labelMediumStyle)

  create()


  /********************
    *       Init      *
    ********************/
  override def create(): Unit = {
    interfaceStage = new Stage(interfaceViewport, root.interfaceSpriteBatch)

    constructHud()

    val entityCamera: OrthographicCamera = new OrthographicCamera(Constants.SCREEN_X, Constants.SCREEN_Y)
    val entityViewport: FitViewport = new FitViewport(Constants.SCREEN_X, Constants.SCREEN_Y, entityCamera)
    entityStage = new EntityStage(this, entityViewport, entityCamera, new SpriteBatch(), controllerHandler)
  }

  private def constructHud(): Unit = {
    val mainTable: Table = new Table
    mainTable.setFillParent(true)
    // mainTable.setDebug(true, true)

    val fpsTable: Table = new Table
    fpsLabel.setAlignment(Align.left, Align.left)
    fpsTable.add(fpsLabel).expand.fill.left.padLeft(12).padTop(8)

    val versionTable: Table = new Table
    val versionLabel: Label = new Label("v0.1 Alpha", Resources.labelMediumStyle)
    versionLabel.setAlignment(Align.right, Align.right)
    versionTable.add(versionLabel).expand.fill.right.padRight(12).padBottom(8)

    mainTable.add(fpsTable).width(Constants.EXACT_X).height(32).top.expand.fill
    mainTable.row
    mainTable.add(versionTable).width(Constants.EXACT_X).height(32).bottom.expand.fill

    interfaceStage.addActor(mainTable)
  }


  /**********************
    * Screen Operations *
    **********************/
  override def render(delta: Float): Unit = {
    Gdx.gl.glClearColor(0.2f, 0.29f, 0.37f, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    entityStage.update(delta)
    if (!paused) {
      entityStage.render(delta)
    }

    val fps = Gdx.graphics.getFramesPerSecond
    fpsLabel.setText(s"FPS: $fps")
    interfaceStage.act(delta)
    interfaceStage.draw()

    tweenManager.update(delta)
  }

  override def show(): Unit = {
    Gdx.input.setInputProcessor(interfaceStage)
    Controllers.clearListeners()
    Controllers.addListener(controllerHandler)
  }

  override def resize(width: Int, height: Int): Unit = {
    super.resize(width, height)
  }

  override def dispose(): Unit = {
    super.dispose()
    entityStage.dispose()
  }
}
