package galenscovell.sandbox.global

import com.badlogic.ashley.core._
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d._
import galenscovell.sandbox.entities.EntityCreator
import galenscovell.sandbox.entities.components._
import galenscovell.sandbox.entities.components.dynamic.{DayPassedComponent, InteractiveComponent, RemovalComponent}
import galenscovell.sandbox.entities.systems._
import galenscovell.sandbox.global.enums.Season
import galenscovell.sandbox.processing.input.ControllerHandler
import galenscovell.sandbox.ui.components.EntityStage


object EntityManager {
  private val engine: Engine = new Engine
  private var creator: EntityCreator = _


  /********************
    *       Get       *
    ********************/
  def getEngine: Engine = engine

  def getCreator: EntityCreator = creator


  /********************
    *      Update     *
    ********************/
  def update(delta: Float): Unit = {
    engine.update(delta)
  }

  def addDayPassedToAllGrowables(season: Season.Value, day: Int): Unit = {
    val growables: ImmutableArray[Entity] = engine.getEntitiesFor(
      Family.all(classOf[GrowableComponent]).get()
    )

    growables.forEach(e => e.add(new DayPassedComponent(season, day)))
  }

  def addInteractiveComponent(entity: Entity): Unit = {
    if (entity.getComponent(classOf[InteractiveComponent]) == null) {
      entity.add(new InteractiveComponent)
    }
  }

  def addRemovalComponent(entity: Entity): Unit ={
    if (entity.getComponent(classOf[RemovalComponent]) == null) {
      entity.add(new RemovalComponent)
    }
  }


  /********************
    *    Creation     *
    ********************/
  def setup(entitySpriteBatch: SpriteBatch,
            controllerHandler: ControllerHandler,
            world: World,
            entityStage: EntityStage): Unit = {
    creator = new EntityCreator(engine, world)

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

    // Handles crop collision/input interactions
    val cropInteractionSystem: CropInteractionSystem = new CropInteractionSystem(
      Family.all(
        classOf[BodyComponent],
        classOf[GrowableComponent],
        classOf[InteractiveComponent],
        classOf[StateComponent]
      ).get(), world, controllerHandler
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
        classOf[BodyComponent],
        classOf[RemovalComponent]
      ).get(), world
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
    engine.addSystem(animationSystem)

    renderSystem.priority = 1
    engine.addSystem(renderSystem)

    playerSystem.priority = 2
    engine.addSystem(playerSystem)

    cropInteractionSystem.priority = 3
    engine.addSystem(cropInteractionSystem)

    // collisionSystem.priority = 4
    // engine.addSystem(collisionSystem)

    characterStateSystem.priority = 5
    engine.addSystem(characterStateSystem)

    animalStateSystem.priority = 6
    engine.addSystem(animalStateSystem)

    cropStateSystem.priority = 7
    engine.addSystem(cropStateSystem)

    removalSystem.priority = 8
    engine.addSystem(removalSystem)
  }
}
