package galenscovell.sandbox.ui.components

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import galenscovell.sandbox.entities.components.{BodyComponent, SteeringComponent}
import galenscovell.sandbox.environment.Physics
import galenscovell.sandbox.global.enums.Crop
import galenscovell.sandbox.global.{Clock, Constants, EntityManager}
import galenscovell.sandbox.processing.BaseSteerable
import galenscovell.sandbox.processing.input.ControllerHandler
import galenscovell.sandbox.ui.screens.GameScreen


class EntityStage(val gameScreen: GameScreen,
                  val entityViewport: FitViewport,
                  val entityCamera: OrthographicCamera,
                  val entitySpriteBatch: SpriteBatch,
                  val controllerHandler: ControllerHandler) extends Stage(entityViewport, entitySpriteBatch) {
  private val physics: Physics = new Physics

  // For camera
  private val lerpPos: Vector3 = new Vector3(0, 0, 0)
  private var minCamX, minCamY, maxCamX, maxCamY: Float = 0f
  private var playerBody: Body = _

  // Box2D has a limit on velocity of 2.0 units per step
  // The max speed is 120m/s at 60fps
  private val timeStep: Float = 1 / 120.0f
  private var accumulator: Float = 0

  create()


  /********************
    *       Init      *
    ********************/
  private def create(): Unit = {
    EntityManager.setup(entitySpriteBatch, controllerHandler, physics.getWorld, this)

    // Establish player entity
    val player: Entity = EntityManager.getCreator.createPlayer(0, 0)
    playerBody = player.getComponent(classOf[BodyComponent]).body
    val playerSteerable: BaseSteerable = player.getComponent(classOf[SteeringComponent]).getSteerable

    EntityManager.getCreator.createCrop(Crop.Corn, -1, 3, Clock.getDay, Constants.MEDIUM_ENTITY_SIZE, Constants.SMALL_ENTITY_SIZE)
    EntityManager.getCreator.createCrop(Crop.Corn, 0, 3, Clock.getDay, Constants.MEDIUM_ENTITY_SIZE, Constants.SMALL_ENTITY_SIZE)
    EntityManager.getCreator.createCrop(Crop.Corn, 1, 3, Clock.getDay, Constants.MEDIUM_ENTITY_SIZE, Constants.SMALL_ENTITY_SIZE)
    EntityManager.getCreator.createCrop(Crop.Corn, 2, 3, Clock.getDay, Constants.MEDIUM_ENTITY_SIZE, Constants.SMALL_ENTITY_SIZE)

    // Start camera centered on player
    entityCamera.position.set(playerBody.getPosition.x, playerBody.getPosition.y, 0)
  }


  /********************
    *     Update      *
    ********************/
  def render(delta: Float): Unit = {
    val frameTime: Float = Math.min(delta, 0.25f)
    accumulator += frameTime
    while (accumulator > timeStep) {
      physics.update(timeStep)
      accumulator -= timeStep

      entitySpriteBatch.begin()
      EntityManager.update(delta)
      entitySpriteBatch.end()

      gameScreen.updateFpsCounter()
    }

    updateCamera()
    entitySpriteBatch.setProjectionMatrix(entityCamera.combined)

    physics.debugRender(entityCamera.combined)
  }

  def update(delta: Float): Unit = {
    act(delta)
    draw()
  }


  /**********************
    *      Camera       *
    **********************/
  def updateCamera(): Unit = {
    // Find camera upper left coordinates
    minCamX = entityCamera.position.x - (entityCamera.viewportWidth / 2) * entityCamera.zoom
    minCamY = entityCamera.position.y - (entityCamera.viewportHeight / 2) * entityCamera.zoom

    // Find camera lower right coordinates
    maxCamX = minCamX + entityCamera.viewportWidth * entityCamera.zoom
    maxCamY = minCamY + entityCamera.viewportHeight * entityCamera.zoom

    entityCamera.update()

    centerCameraOnPlayer()
  }

  private def centerCameraOnPlayer(): Unit = {
    lerpPos.x = playerBody.getPosition.x
    lerpPos.y = playerBody.getPosition.y

    entityCamera.position.lerp(lerpPos, 0.1f)
  }

  def inCamera(x: Float, y: Float): Boolean = {
    // Determines if a point falls within the camera
    // (+/- medium entity size to reduce chance of pop-in)
    (x + Constants.MEDIUM_ENTITY_SIZE) >= minCamX &&
      (y + Constants.MEDIUM_ENTITY_SIZE) >= minCamY &&
      (x - Constants.MEDIUM_ENTITY_SIZE) <= maxCamX &&
      (y - Constants.MEDIUM_ENTITY_SIZE) <= maxCamY
  }

  override def dispose(): Unit = {
    super.dispose()
    physics.dispose()
  }
}
