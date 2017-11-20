package galenscovell.sandbox.entities

import com.badlogic.ashley.core.{Engine, Entity}
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.{JsonReader, JsonValue}
import galenscovell.sandbox.entities.components._
import galenscovell.sandbox.global.{Constants, Resources}
import galenscovell.sandbox.global.enums.{Crop, Direction, Season}
import galenscovell.sandbox.graphics.Animation
import galenscovell.sandbox.states.{CropAgent, PlayerAgent}

import scala.collection.mutable


class EntityCreator(engine: Engine, world: World) {
  private val jsonReader: JsonReader = new JsonReader()
  private val characterSource: String = "data/characters.json"
  private val cropSource: String = "data/crops.json"
  private val gatherableSource: String = "data/gatherables.json"


  def makePlayer(posX: Float, posY: Float): Entity = {
    val entity: Entity = new Entity
    val bodyComponent: BodyComponent = new BodyComponent(
      entity, world, posX, posY, Constants.MEDIUM_ENTITY_SIZE, movable=true, soft=true
    )

    // Assemble animation map
    val animationMap: mutable.Map[String, Animation] = mutable.Map[String, Animation]()

    // Default
    Resources.generateAnimationAndAddToMap(animationMap, "player", PlayerAgent.DEFAULT, Direction.RIGHT,
      List((0, 0)), loop = false)
    Resources.generateAnimationAndAddToMap(animationMap, "player", PlayerAgent.DEFAULT, Direction.DOWN,
      List((0, 0)), loop = false)
    Resources.generateAnimationAndAddToMap(animationMap, "player", PlayerAgent.DEFAULT, Direction.LEFT,
      List((0, 0)), loop = false)
    Resources.generateAnimationAndAddToMap(animationMap, "player", PlayerAgent.DEFAULT, Direction.UP,
      List((0, 0)), loop = false)

    // Walk
    Resources.generateAnimationAndAddToMap(animationMap, "player", PlayerAgent.WALK, Direction.RIGHT,
      List((0, 0.4f), (1, 0.5f), (0, 0.4f), (3, 0.5f)), loop = true)
    Resources.generateAnimationAndAddToMap(animationMap, "player", PlayerAgent.WALK, Direction.DOWN,
      List((0, 0.4f), (1, 0.5f), (0, 0.4f), (3, 0.5f)), loop = true)
    Resources.generateAnimationAndAddToMap(animationMap, "player", PlayerAgent.WALK, Direction.LEFT,
      List((0, 0.4f), (1, 0.5f), (0, 0.4f), (3, 0.5f)), loop = true)
    Resources.generateAnimationAndAddToMap(animationMap, "player", PlayerAgent.WALK, Direction.UP,
      List((0, 0.4f), (1, 0.5f), (0, 0.4f), (3, 0.5f)), loop = true)

    // Run
    Resources.generateAnimationAndAddToMap(animationMap, "player", PlayerAgent.RUN, Direction.RIGHT,
      List((0, 0.3f), (1, 0.4f), (0, 0.3f), (3, 0.4f)), loop = true)
    Resources.generateAnimationAndAddToMap(animationMap, "player", PlayerAgent.RUN, Direction.DOWN,
      List((0, 0.3f), (1, 0.4f), (0, 0.3f), (3, 0.4f)), loop = true)
    Resources.generateAnimationAndAddToMap(animationMap, "player", PlayerAgent.RUN, Direction.LEFT,
      List((0, 0.3f), (1, 0.4f), (0, 0.3f), (3, 0.4f)), loop = true)
    Resources.generateAnimationAndAddToMap(animationMap, "player", PlayerAgent.RUN, Direction.UP,
      List((0, 0.3f), (1, 0.4f), (0, 0.3f), (3, 0.4f)), loop = true)

    entity.add(new AnimationComponent(animationMap.toMap))
    entity.add(bodyComponent)
    entity.add(new MovementComponent)
    entity.add(new PlayerComponent)
    entity.add(new SizeComponent(Constants.SMALL_ENTITY_SIZE, Constants.MEDIUM_ENTITY_SIZE))
    entity.add(new TextureComponent())
    entity.add(new StateComponent(PlayerAgent.DEFAULT))
    entity.add(new SteeringComponent(bodyComponent.body, Constants.MEDIUM_ENTITY_SIZE, 10, 10))

    engine.addEntity(entity)
    entity
  }

  def makeCrop(crop: Crop.Value,
               posX: Float,
               posY: Float,
               dayPlanted: Int,
               height: Float,
               bodySize: Float): Unit = {
    val entity: Entity = new Entity
    val bodyComponent: BodyComponent = new BodyComponent(
      entity, world, posX, posY, bodySize, movable=false, soft=true
    )

    // Assemble animation map
    val animationMap: mutable.Map[String, Animation] = mutable.Map[String, Animation]()

    entity.add(new AnimationComponent(animationMap.toMap))
    entity.add(bodyComponent)
    entity.add(new StateComponent(CropAgent.SEED))
    entity.add(new SizeComponent(Constants.TILE_SIZE, height))

    // Pull components from JSON data
    val reader: JsonValue = jsonReader.parse(Gdx.files.internal(cropSource))
    val json: JsonValue = reader.get(crop.toString)

    val season: Season.Value = Season.withName(json.getString("season"))
    val buyCost: Int = json.getInt("buyCost")
    val sellCost: Int = json.getInt("sellCost")

    val days: JsonValue = json.get("days")
    val daysToSprout: Int = days.getInt("toSprout")
    val daysToImmature: Int = days.getInt("toImmature")
    val daysToMature: Int = days.getInt("toMature")
    val daysToHarvest: Int = days.getInt("toHarvest")

    entity.add(new GrowableComponent(dayPlanted, season, daysToSprout, daysToImmature, daysToMature, daysToHarvest))
    entity.add(new CollectableComponent(buyCost, sellCost))
    entity.add(new TextureComponent())

    engine.addEntity(entity)
  }
}
