package galenscovell.sandbox.global

import com.badlogic.ashley.core._
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d._
import galenscovell.sandbox.entities.components._
import galenscovell.sandbox.entities.components.dynamic.{DayPassedComponent, RemovalComponent}
import galenscovell.sandbox.entities.systems._
import galenscovell.sandbox.global.enums.Season
import galenscovell.sandbox.processing.input.ControllerHandler
import galenscovell.sandbox.ui.components.EntityStage


object EntityManager {
  private val ecsEngine: Engine = new Engine


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

  def addDayPassedToGrowables(season: Season.Value, day: Int): Unit = {
    val growables: ImmutableArray[Entity] = ecsEngine.getEntitiesFor(
      Family.all(classOf[GrowableComponent]).get()
    )

    growables.forEach(e => e.add(new DayPassedComponent(season, day)))
  }


  /********************
    *    Creation     *
    ********************/
  def setupSystems(entitySpriteBatch: SpriteBatch,
                   controllerHandler: ControllerHandler,
                   world: World,
                   entityStage: EntityStage): Unit = {
    // Handles animal AI
    val animalStateSystem: AnimalStateSystem = new AnimalStateSystem(
      Family.all(
        classOf[AnimalComponent],
        classOf[BodyComponent],
        classOf[MovementComponent],
        classOf[SteeringComponent],
        classOf[StateComponent]
      ).get()
    )

    // Handles animations
    val animationSystem: AnimationSystem = new AnimationSystem(
      Family.all(
        classOf[AnimationComponent],
        classOf[StateComponent],
        classOf[TextureComponent]
      ).get()
    )

    // Handles NPC AI
    val characterStateSystem: CharacterStateSystem = new CharacterStateSystem(
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

    // Handles crop growth
    val cropStateSystem: CropStateSystem = new CropStateSystem(
      Family.all(
        classOf[DayPassedComponent],
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
        classOf[StateComponent]
      ).get(), controllerHandler
    )

    // Handles removal of entities
    val removalSystem: RemovalSystem = new RemovalSystem(
      Family.all(
        classOf[RemovalComponent]
      ).get()
    )

    // Handles entity graphics
    val renderSystem: RenderSystem = new RenderSystem(
      Family.all(
        classOf[BodyComponent],
        classOf[SizeComponent],
        classOf[TextureComponent]
      ).get(), entitySpriteBatch, entityStage
    )

    // Add systems to engine with priorities
    animationSystem.priority = 0
    ecsEngine.addSystem(animationSystem)

    renderSystem.priority = 1
    ecsEngine.addSystem(renderSystem)

    playerSystem.priority = 2
    ecsEngine.addSystem(playerSystem)

    collisionSystem.priority = 3
    ecsEngine.addSystem(collisionSystem)

    characterStateSystem.priority = 4
    ecsEngine.addSystem(characterStateSystem)

    animalStateSystem.priority = 5
    ecsEngine.addSystem(animalStateSystem)

    cropStateSystem.priority = 6
    ecsEngine.addSystem(cropStateSystem)

    removalSystem.priority = 7
    ecsEngine.addSystem(removalSystem)
  }
}
