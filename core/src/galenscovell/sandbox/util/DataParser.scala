package galenscovell.sandbox.util

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.{JsonReader, JsonValue}
import galenscovell.sandbox.ecs.component.{CollectableComponent, GrowableComponent, SpriteComponent}
import galenscovell.sandbox.enums.{Crop, Season}


class DataParser {
  private val characterSource: String = "data/characters.json"
  private val cropSource: String = "data/crops.json"
  private val gatherableSource: String = "data/gatherables.json"


  def parseCharacter(name: String, entity: Entity): Unit = {
    val reader: JsonValue = new JsonReader().parse(Gdx.files.internal(characterSource))
    val json: JsonValue = reader.get(name)
  }

  def parseCrop(crop: Crop.Value, entity: Entity): Entity = {
    val reader: JsonValue = new JsonReader().parse(Gdx.files.internal(cropSource))
    val json: JsonValue = reader.get(crop.toString)

    val season: Season.Value = Season.withName(json.getString("season"))
    val days: Int = json.getInt("days")
    val buyCost: Int = json.getInt("buyCost")
    val sellCost: Int = json.getInt("sellCost")
    val sprite: String = json.getString("sprite")

    entity.add(new GrowableComponent(days, season))
    entity.add(new CollectableComponent(buyCost, sellCost))
    entity.add(new SpriteComponent(sprite))

    entity
  }

  def parseGatherable(name: String, entity: Entity): Entity = {
    val reader: JsonValue = new JsonReader().parse(Gdx.files.internal(gatherableSource))
    val json: JsonValue = reader.get(name)

    entity
  }
}
