package galenscovell.sandbox.ecs

import com.badlogic.ashley.core.{Engine, Entity}
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.{JsonReader, JsonValue}
import galenscovell.sandbox.ecs.component._
import galenscovell.sandbox.enums.{Crop, Season}
import galenscovell.sandbox.singletons.Constants
import galenscovell.sandbox.stateMachines.{CropAgent, PlayerAgent}


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

    entity.add(bodyComponent)
    entity.add(new MovementComponent)
    entity.add(new PlayerComponent)
    entity.add(new RenderableComponent)
    entity.add(new SizeComponent(Constants.SMALL_ENTITY_SIZE, Constants.MEDIUM_ENTITY_SIZE))
    entity.add(new SpriteComponent("player"))
    entity.add(new StateComponent(PlayerAgent.DEFAULT))
    entity.add(new SteeringComponent(bodyComponent.body, Constants.MEDIUM_ENTITY_SIZE, 10, 10))

    engine.addEntity(entity)
    entity
  }

  def makeGatherable(posX: Float, posY: Float): Unit = {
    var entity: Entity = new Entity
    val bodyComponent: BodyComponent = new BodyComponent(
      entity, world, posX, posY, Constants.TILE_SIZE, movable=false, soft=true
    )

    entity.add(bodyComponent)
    entity.add(new RenderableComponent)
    entity.add(new SizeComponent(Constants.TILE_SIZE, Constants.TILE_SIZE))
    entity.add(new SpriteComponent("sprite"))

    // Pull components from JSON data
    val reader: JsonValue = jsonReader.parse(Gdx.files.internal(gatherableSource))
    val json: JsonValue = reader.get("")

    engine.addEntity(entity)
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

    entity.add(bodyComponent)
    entity.add(new RenderableComponent)
    entity.add(new StateComponent(CropAgent.SEED))
    entity.add(new SizeComponent(Constants.TILE_SIZE, height))

    // Pull components from JSON data
    val reader: JsonValue = jsonReader.parse(Gdx.files.internal(cropSource))
    val json: JsonValue = reader.get(crop.toString)

    val season: Season.Value = Season.withName(json.getString("season"))

    val days: JsonValue = json.get("days")
    val daysToSprout: Int = days.getInt("toSprout")
    val daysToImmature: Int = days.getInt("toImmature")
    val daysToMature: Int = days.getInt("toMature")

    val buyCost: Int = json.getInt("buyCost")
    val sellCost: Int = json.getInt("sellCost")
    val sprite: String = json.getString("sprite")

    entity.add(new GrowableComponent(dayPlanted, season, daysToSprout, daysToImmature, daysToMature))
    entity.add(new CollectableComponent(buyCost, sellCost))
    entity.add(new SpriteComponent(sprite))

    engine.addEntity(entity)
  }

  def makeCharacter(): Unit = {
    // Pull components from JSON data
    val reader: JsonValue = jsonReader.parse(Gdx.files.internal(characterSource))
    val json: JsonValue = reader.get("")
  }

  def makeAnimal(): Unit = {

  }
}
