package galenscovell.sandbox.ecs

import com.badlogic.ashley.core._
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d._
import galenscovell.sandbox.ecs.component._
import galenscovell.sandbox.ecs.system._
import galenscovell.sandbox.environment.DateTime
import galenscovell.sandbox.processing.input.ControllerHandler
import galenscovell.sandbox.ui.component.EntityStage


class EntityManager(entitySpriteBatch: SpriteBatch,
                    controllerHandler: ControllerHandler,
                    world: World,
                    dateTime: DateTime,
                    entityStage: EntityStage) {
  private val ecsEngine: Engine = new Engine

  setupSystems()


  /********************
    *       Get       *
    ********************/
  def getEngine: Engine = ecsEngine


  /********************
    *      Update     *
    ********************/
  def update(delta: Float): Unit = {
    ecsEngine.update(delta)
  }


  /********************
    *    Creation     *
    ********************/
  private def setupSystems(): Unit = {
    // Handles animal AI and state TODO: Pass Time here
    val animalSystem: AnimalSystem = new AnimalSystem(
      Family.all(
        classOf[AnimalComponent],
        classOf[BodyComponent],
        classOf[MovementComponent],
        classOf[SteeringComponent],
        classOf[StateComponent]
      ).get()
    )

    // Handles NPC AI and state TODO: Pass Time here
    val characterSystem: CharacterSystem = new CharacterSystem(
      Family.all(
        classOf[CharacterComponent],
        classOf[BodyComponent],
        classOf[MovementComponent],
        classOf[SteeringComponent],
        classOf[StateComponent]
      ).get()
    )

    // Handles collisions
    val collisionSystem: CollisionSystem = new CollisionSystem(
      Family.all(
        classOf[BodyComponent],
      ).get(), world
    )

    // Handles crop growth TODO: Pass Time here
    val cropSystem: CropSystem = new CropSystem(
      Family.all(
        classOf[GrowableComponent],
        classOf[StateComponent]
      ).get()
    )

    // Handles player controls
    val playerSystem: PlayerSystem = new PlayerSystem(
      Family.all(
        classOf[BodyComponent],
        classOf[MovementComponent],
        classOf[PlayerComponent],
      ).get(), controllerHandler
    )

    // Handles entity graphics
    val renderSystem: RenderSystem = new RenderSystem(
      Family.all(
        classOf[BodyComponent],
        classOf[RenderableComponent],
        classOf[SizeComponent],
        classOf[SpriteComponent]
      ).get(), entitySpriteBatch, entityStage
    )

    ecsEngine.addSystem(animalSystem)
    ecsEngine.addSystem(characterSystem)
    ecsEngine.addSystem(collisionSystem)
    ecsEngine.addSystem(cropSystem)
    ecsEngine.addSystem(playerSystem)
    ecsEngine.addSystem(renderSystem)
  }
}
