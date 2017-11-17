package galenscovell.sandbox.singletons

import com.badlogic.ashley.core._
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d._
import galenscovell.sandbox.ecs.component._
import galenscovell.sandbox.ecs.component.dynamic.{DayPassedComponent, RemovalComponent}
import galenscovell.sandbox.ecs.system._
import galenscovell.sandbox.enums.Season
import galenscovell.sandbox.processing.input.ControllerHandler
import galenscovell.sandbox.ui.component.EntityStage


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
    // Handles animal AI and state
    val animalSystem: AnimalSystem = new AnimalSystem(
      Family.all(
        classOf[AnimalComponent],
        classOf[BodyComponent],
        classOf[MovementComponent],
        classOf[SteeringComponent],
        classOf[StateComponent]
      ).get()
    )

    // Handles NPC AI and state
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

    // Handles crop growth and state
    val cropSystem: CropSystem = new CropSystem(
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
        classOf[RenderableComponent],
        classOf[SizeComponent],
        classOf[SpriteComponent]
      ).get(), entitySpriteBatch, entityStage
    )

    // Add all systems to engine with priorities
    renderSystem.priority = 0
    ecsEngine.addSystem(renderSystem)

    playerSystem.priority = 1
    ecsEngine.addSystem(playerSystem)

    collisionSystem.priority = 2
    ecsEngine.addSystem(collisionSystem)

    characterSystem.priority = 3
    ecsEngine.addSystem(characterSystem)

    animalSystem.priority = 4
    ecsEngine.addSystem(animalSystem)

    cropSystem.priority = 5
    ecsEngine.addSystem(cropSystem)

    removalSystem.priority = 6
    ecsEngine.addSystem(removalSystem)
  }
}
