package galenscovell.sandbox.ui.screens

import aurelienribon.tweenengine.TweenManager
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.{GL20, OrthographicCamera}
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.{Label, Table}
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.{Gdx, InputMultiplexer}
import galenscovell.sandbox.Main
import galenscovell.sandbox.global.{Clock, Constants, Resources}
import galenscovell.sandbox.processing.input.ControllerHandler
import galenscovell.sandbox.ui.components.{EntityStage, InventoryTable}


class GameScreen(root: Main) extends AbstractScreen(root) {
  private var entityStage: EntityStage = _
  private val tweenManager: TweenManager = new TweenManager

  private val inputMultiplexer: InputMultiplexer = new InputMultiplexer
  private val controllerHandler: ControllerHandler = new ControllerHandler(this)
  private var paused: Boolean = false

  private val fpsLabel: Label = new Label("FPS", Resources.labelSmallStyle)
  private val dateLabel: Label = new Label(Clock.getDateStamp, Resources.labelMediumStyle)
  private val timeLabel: Label = new Label(Clock.getTimeStamp, Resources.labelSmallStyle)

  private var inventoryTable: InventoryTable = _

  create()


  /********************
    *       Init      *
    ********************/
  override def create(): Unit = {
    uiStage = new Stage(uiViewport, root.uiSpriteBatch)
    constructUi()

    val entityCamera: OrthographicCamera = new OrthographicCamera(Constants.SCREEN_X, Constants.SCREEN_Y)
    val entityViewport: FitViewport = new FitViewport(Constants.SCREEN_X, Constants.SCREEN_Y, entityCamera)
    entityStage = new EntityStage(this, entityViewport, entityCamera, new SpriteBatch(), controllerHandler)
  }

  private def constructUi(): Unit = {
    val mainTable: Table = new Table
    mainTable.setFillParent(true)
    mainTable.setDebug(true, true)

    val topTable: Table = new Table
    topTable.setDebug(true, true)

    val topTopTable: Table = new Table
    fpsLabel.setAlignment(Align.left, Align.left)
    dateLabel.setAlignment(Align.right, Align.right)

    topTopTable.add(fpsLabel).expand.fill.left.padLeft(16)
    topTopTable.add(dateLabel).expand.fill.right.padRight(16)

    val topBottomTable: Table = new Table
    timeLabel.setAlignment(Align.right, Align.right)
    topBottomTable.add(timeLabel).expand.fill.right.padRight(16)

    topTable.add(topTopTable).expand.fill.top
    topTable.row
    topTable.add(topBottomTable).expand.fill.bottom

    val versionTable: Table = new Table
    val versionLabel: Label = new Label("v0.1 Alpha", Resources.labelSmallStyle)
    versionLabel.setAlignment(Align.right, Align.right)
    versionTable.add(versionLabel).expand.fill.right.padRight(16)

    mainTable.add(topTable).width(Constants.EXACT_X).height(80).top.expand.fill.padTop(8)
    mainTable.row
    mainTable.add(versionTable).width(Constants.EXACT_X).height(32).bottom.expand.fill.padBottom(8)

    uiStage.addActor(mainTable)


    inventoryTable = new InventoryTable(uiStage)
  }

  def updateFpsCounter(): Unit = {
    val fps = Gdx.graphics.getFramesPerSecond
    fpsLabel.setText(s"FPS: $fps")
  }

  def pause(setting: Boolean): Unit = {
    paused = setting
  }


  /**********************
    *     Dynamic UI    *
    **********************/
  def toggleInventory(): Unit = {
    if (inventoryTable.hasParent) {
      println("Toggle inventory: OFF")
      inventoryTable.remove()
    } else {
      println("Toggle inventory: ON")
      uiStage.addActor(inventoryTable)
    }
  }


  /**********************
    * Screen Operations *
    **********************/
  override def render(delta: Float): Unit = {
    Gdx.gl.glClearColor(0.2f, 0.29f, 0.37f, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    if (!paused) {
      entityStage.render(delta)
      entityStage.update(delta)
    }

    Clock.update(delta, dateLabel, timeLabel)

    uiStage.act(delta)
    uiStage.draw()

    tweenManager.update(delta)
  }

  override def show(): Unit = {
    Gdx.input.setInputProcessor(uiStage)
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
