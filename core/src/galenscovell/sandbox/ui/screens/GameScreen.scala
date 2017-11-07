package galenscovell.sandbox.ui.screens

import aurelienribon.tweenengine.TweenManager
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.{GL20, OrthographicCamera}
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.scenes.scene2d.Stage
import galenscovell.sandbox.Program
import galenscovell.sandbox.ecs.component.BodyComponent
import galenscovell.sandbox.ecs.{EntityCreator, EntityManager}
import galenscovell.sandbox.environment.Physics
import galenscovell.sandbox.processing.ControllerHandler
import galenscovell.sandbox.util.Constants


class GameScreen(root: Program) extends AbstractScreen(root) {
  private val entitySpriteBatch: SpriteBatch = new SpriteBatch()
  private val worldCamera: OrthographicCamera = new OrthographicCamera(Constants.SCREEN_X, Constants.SCREEN_Y)

  private val tweenManager: TweenManager = new TweenManager
  private val controllerHandler: ControllerHandler = new ControllerHandler
  private val physics: Physics = new Physics
  private val entityManager: EntityManager = new EntityManager(entitySpriteBatch, controllerHandler, physics.getWorld, this)

  // Box2d has a limit on velocity of 2.0 units per step
  // The max speed is 120m/s at 60fps
  private val timeStep: Float = 1 / 120.0f
  private var accumulator: Float = 0

  // For camera
  private val lerpPos: Vector3 = new Vector3(0, 0, 0)
  private var minCamX, minCamY, maxCamX, maxCamY: Float = 0f
  private var playerBody: Body = _

  create()


  /********************
    *       Init      *
    ********************/
  override def create(): Unit = {
    stage = new Stage(viewport, root.uiSpriteBatch)

    val entityCreator: EntityCreator = new EntityCreator(entityManager.getEngine, physics.getWorld)

    // Establish player entity
    val player: Entity = entityCreator.makePlayer(0, 0)
    playerBody = player.getComponent(classOf[BodyComponent]).body

    entityCreator.makeGatherable(-1, 3)
    entityCreator.makeGatherable(0, 3)
    entityCreator.makeGatherable(1, 3)
    entityCreator.makeGatherable(2, 3)

    // Start camera centered on player
    worldCamera.position.set(playerBody.getPosition.x, playerBody.getPosition.y, 0)
  }


  /**********************
    *      Camera       *
    **********************/
  private def updateCamera(): Unit = {
    // Find camera upper left coordinates
    minCamX = worldCamera.position.x - (worldCamera.viewportWidth / 2) * worldCamera.zoom
    minCamY = worldCamera.position.y - (worldCamera.viewportHeight / 2) * worldCamera.zoom

    // Find camera lower right coordinates
    maxCamX = minCamX + worldCamera.viewportWidth * worldCamera.zoom
    maxCamY = minCamY + worldCamera.viewportHeight * worldCamera.zoom

    worldCamera.update()
  }

  private def centerCameraOnPlayer(): Unit = {
    lerpPos.x = playerBody.getPosition.x
    lerpPos.y = playerBody.getPosition.y

    worldCamera.position.lerp(lerpPos, 0.05f)
  }

  def inCamera(x: Float, y: Float): Boolean = {
    // Determines if a point falls within the camera
    // (+/- medium entity size to reduce chance of pop-in)
    (x + Constants.MEDIUM_ENTITY_SIZE) >= minCamX &&
      (y + Constants.MEDIUM_ENTITY_SIZE) >= minCamY &&
      (x - Constants.MEDIUM_ENTITY_SIZE) <= maxCamX &&
      (y - Constants.MEDIUM_ENTITY_SIZE) <= maxCamY
  }


  /**********************
    * Screen Operations *
    **********************/
  override def render(delta: Float): Unit = {
    Gdx.gl.glClearColor(0.2f, 0.29f, 0.37f, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    // Frame rate and physics time step
    val frameTime: Float = Math.min(delta, 0.25f)
    accumulator += frameTime
    while (accumulator > timeStep) {
      physics.update(timeStep)
      accumulator -= timeStep
    }

    // Camera operations
    updateCamera()
    centerCameraOnPlayer()
    entitySpriteBatch.setProjectionMatrix(worldCamera.combined)

    // ECS rendering
    entitySpriteBatch.begin()
    entityManager.update(delta)
    entitySpriteBatch.end()

    // Tween operations
    tweenManager.update(delta)

    physics.debugRender(worldCamera.combined)
  }

  override def show(): Unit = {
    Gdx.input.setInputProcessor(stage)
    Controllers.clearListeners()
    Controllers.addListener(controllerHandler)
  }

  override def resize(width: Int, height: Int): Unit = {
    super.resize(width, height)
  }

  override def dispose(): Unit = {
    super.dispose()
    physics.dispose()
  }
}
