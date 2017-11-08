package galenscovell.sandbox.util

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.{JsonReader, JsonValue}


class DataParser {
  private val characterSource: String = "data/characters.json"
  private val cropSource: String = "data/crops.json"
  private val gatherableSource: String = "data/gatherables.json"


  def parseCharacter(name: String, e: Entity): Unit = {
    val reader: JsonValue = new JsonReader().parse(Gdx.files.internal(characterSource))
    val json: JsonValue = reader.get(name)
  }

  def parseCrop(name: String, e: Entity): Entity = {
    val reader: JsonValue = new JsonReader().parse(Gdx.files.internal(cropSource))
    val json: JsonValue = reader.get(name)

    e
  }

  def parseGatherable(name: String, e: Entity): Entity = {
    val reader: JsonValue = new JsonReader().parse(Gdx.files.internal(gatherableSource))
    val json: JsonValue = reader.get(name)

    e
  }
}
